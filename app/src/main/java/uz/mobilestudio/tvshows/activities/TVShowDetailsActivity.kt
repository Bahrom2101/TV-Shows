package uz.mobilestudio.tvshows.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import io.reactivex.CompletableObserver
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import uz.mobilestudio.tvshows.R
import uz.mobilestudio.tvshows.adapters.EpisodesAdapter
import uz.mobilestudio.tvshows.adapters.ImageSliderAdapter
import uz.mobilestudio.tvshows.databinding.ActivityTvshowDetailsBinding
import uz.mobilestudio.tvshows.databinding.ItemContainerEpisodesBinding
import uz.mobilestudio.tvshows.databinding.LayoutEpisodesBottomSheetBinding
import uz.mobilestudio.tvshows.models.TVShow
import uz.mobilestudio.tvshows.viewmodels.TVShowDetailsViewModel
import java.lang.Exception
import java.util.*

class TVShowDetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityTvshowDetailsBinding
    lateinit var tvShowDetailsViewModel: TVShowDetailsViewModel
    lateinit var imageSliderAdapter: ImageSliderAdapter
    lateinit var bottomSheetDialog: BottomSheetDialog
    lateinit var layoutEpisodesBottomSheetBinding: LayoutEpisodesBottomSheetBinding
    lateinit var tvShow: TVShow
    var isTVShowAvailableInWatchlist = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTvshowDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tvShow = intent.extras?.getSerializable("tvShow") as TVShow

        doInitialization()
    }

    private fun doInitialization() {
        tvShowDetailsViewModel = ViewModelProvider(this).get(TVShowDetailsViewModel::class.java)
        binding.imageBack.setOnClickListener {
            onBackPressed()
        }
        checkTVShowInWatchlist()
        getTVShowDetails()
    }

    private fun checkTVShowInWatchlist() {
        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(
            tvShowDetailsViewModel.getTVShowFromWatchlist(tvShow.id.toString())
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    isTVShowAvailableInWatchlist = true
                    binding.imageWatchlist.setImageResource(R.drawable.ic_check)
                    compositeDisposable.dispose()
                }
        )
    }

    @SuppressLint("SetTextI18n", "CheckResult")
    private fun getTVShowDetails() {
        tvShowDetailsViewModel.getTVShowDetails(tvShow.id.toString())
            .observe(this, Observer { tvShowDetailsResponse ->
                binding.progress.visibility = View.GONE
                if (tvShowDetailsResponse.tvShowDetails != null) {
                    if (tvShowDetailsResponse.tvShowDetails!!.pictures != null) {
                        loadImageSlider(tvShowDetailsResponse.tvShowDetails!!.pictures!!)
                    }
                    Picasso.get().load(tvShowDetailsResponse.tvShowDetails!!.imagePath).noFade()
                        .into(binding.imageTVShow, object :
                            Callback {
                            override fun onSuccess() {
                                binding.imageTVShow.animate().setDuration(300).alpha(1f)
                                    .start()
                            }

                            override fun onError(e: Exception?) {

                            }
                        })
                    binding.imageTVShow.visibility = View.VISIBLE
                    binding.textDescription.text = HtmlCompat.fromHtml(
                        tvShowDetailsResponse.tvShowDetails!!.description!!,
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    )
                    binding.textDescription.visibility = View.VISIBLE
                    binding.textReadMore.visibility = View.VISIBLE
                    binding.textReadMore.setOnClickListener {
                        if (binding.textReadMore.text.toString() == getString(R.string.read_more)) {
                            binding.textDescription.maxLines = Int.MAX_VALUE
                            binding.textDescription.ellipsize = null
                            binding.textReadMore.text = getString(R.string.read_less)
                        } else {
                            binding.textDescription.maxLines = 4
                            binding.textDescription.ellipsize = TextUtils.TruncateAt.END
                            binding.textReadMore.text = getString(R.string.read_more)
                        }
                    }
                    binding.textRating.text = String.format(
                        Locale.getDefault(),
                        "%.2f",
                        tvShowDetailsResponse.tvShowDetails!!.rating?.toDouble()
                    )
                    if (tvShowDetailsResponse.tvShowDetails?.genres != null) {
                        binding.textGenre.text = tvShowDetailsResponse.tvShowDetails!!.genres!![0]
                    } else {
                        binding.textGenre.text = "N/A"
                    }
                    binding.textRuntime.text = tvShowDetailsResponse.tvShowDetails?.runtime + "Min"
                    binding.viewDivider1.visibility = View.VISIBLE
                    binding.layoutMusic.visibility = View.VISIBLE
                    binding.viewDivider2.visibility = View.VISIBLE

                    binding.buttonWebsite.setOnClickListener {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(tvShowDetailsResponse.tvShowDetails?.url)
                        startActivity(intent)
                    }
                    binding.buttonWebsite.visibility = View.VISIBLE
                    binding.buttonEpisodes.visibility = View.VISIBLE

                    binding.buttonEpisodes.setOnClickListener {
                        bottomSheetDialog = BottomSheetDialog(this)
                        layoutEpisodesBottomSheetBinding =
                            LayoutEpisodesBottomSheetBinding.inflate(layoutInflater)
                        bottomSheetDialog.setContentView(layoutEpisodesBottomSheetBinding.root)
                        layoutEpisodesBottomSheetBinding.episodesRv.adapter =
                            EpisodesAdapter(tvShowDetailsResponse.tvShowDetails!!.episodes!!)
                        layoutEpisodesBottomSheetBinding.textTitle.text =
                            String.format("Episodes | %s", tvShow.name)
                        layoutEpisodesBottomSheetBinding.imageClose.setOnClickListener {
                            bottomSheetDialog.dismiss()
                        }
                        val frameLayout =
                            bottomSheetDialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
                        if (frameLayout != null) {
                            val bottomSheetBehavior: BottomSheetBehavior<View> =
                                BottomSheetBehavior.from(frameLayout)
                            bottomSheetBehavior.peekHeight =
                                Resources.getSystem().displayMetrics.heightPixels
                            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                        }
                        bottomSheetDialog.show()
                    }

                    binding.imageWatchlist.setOnClickListener {
                        val compositeDisposable = CompositeDisposable()
                        if (isTVShowAvailableInWatchlist) {
                            compositeDisposable.add(tvShowDetailsViewModel.removeFromWatchlist(
                                tvShow
                            )
                                .subscribeOn(Schedulers.computation())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe {
                                    isTVShowAvailableInWatchlist = false
                                    binding.imageWatchlist.setImageResource(R.drawable.ic_watchlist)
                                    Toast.makeText(
                                        applicationContext,
                                        "Removed from watchlist",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                    compositeDisposable.dispose()
                                })
                        } else {
                            compositeDisposable.add(tvShowDetailsViewModel.addToWatchList(tvShow)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe {
                                    binding.imageWatchlist.setImageResource(R.drawable.ic_check)
                                    Toast.makeText(
                                        applicationContext,
                                        "Added to watchlist",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                    compositeDisposable.dispose()
                                })
                        }
                    }
                    loadBasicTVShowDetails()
                }
            })
    }

    private fun loadImageSlider(images: List<String>) {
        binding.sliderViewPager.offscreenPageLimit = 1
        imageSliderAdapter = ImageSliderAdapter(images)
        binding.sliderViewPager.adapter = imageSliderAdapter
        binding.sliderViewPager.visibility = View.VISIBLE
        binding.viewFadingEdge.visibility = View.VISIBLE
        setupSliderIndicator(images.size)
        binding.sliderViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentSliderIndicator(position)
            }
        })
    }

    private fun setupSliderIndicator(count: Int) {
        val indicators = arrayOfNulls<ImageView>(count)
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i]?.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext, R.drawable.background_slider_indicator_inactive
                )
            )
            indicators[i]?.layoutParams = layoutParams
            binding.layoutSliderIndicator.addView(indicators[i])
        }
        binding.layoutSliderIndicator.visibility = View.VISIBLE
    }

    private fun setCurrentSliderIndicator(position: Int) {
        val childCount = binding.layoutSliderIndicator.childCount
        for (i in 0 until childCount) {
            val imageView = binding.layoutSliderIndicator.getChildAt(i) as ImageView
            if (i == position) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.background_slider_indicator_active
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.background_slider_indicator_inactive
                    )
                )
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun loadBasicTVShowDetails() {
        binding.textName.text = tvShow.name
        binding.textNetworkCountry.text =
            tvShow.network + " (" + tvShow.country + ")"
        binding.textStatus.text = tvShow.status
        binding.textStarted.text = tvShow.startDate
        binding.textName.visibility = View.VISIBLE
        binding.textNetworkCountry.visibility = View.VISIBLE
        binding.textStatus.visibility = View.VISIBLE
        binding.textStarted.visibility = View.VISIBLE
    }

}