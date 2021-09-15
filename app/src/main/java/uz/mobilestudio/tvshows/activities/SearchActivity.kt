package uz.mobilestudio.tvshows.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import uz.mobilestudio.tvshows.adapters.TVShowsAdapter
import uz.mobilestudio.tvshows.databinding.ActivitySearchBinding
import uz.mobilestudio.tvshows.listeners.TVShowsListener
import uz.mobilestudio.tvshows.models.TVShow
import uz.mobilestudio.tvshows.viewmodels.MostPopularTVShowsViewModel
import uz.mobilestudio.tvshows.viewmodels.SearchViewModel
import java.util.*
import kotlin.collections.ArrayList

class SearchActivity : AppCompatActivity() {

    lateinit var binding: ActivitySearchBinding
    lateinit var viewModel: SearchViewModel
    lateinit var tvShows: ArrayList<TVShow>
    lateinit var tvShowsAdapter: TVShowsAdapter
    var currentPage = 1
    var totalAvailablePages = 1
    var timer: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progress1.visibility = View.VISIBLE

        tvShows = ArrayList()

        doInitialization()
    }

    private fun doInitialization() {
        binding.imageBack.setOnClickListener { onBackPressed() }
        binding.searchRv.setHasFixedSize(true)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        tvShowsAdapter = TVShowsAdapter(tvShows, object : TVShowsListener {
            override fun onTVShowClicked(tvShow: TVShow) {
                val intent = Intent(this@SearchActivity, TVShowDetailsActivity::class.java)
                intent.putExtra("tvShow", tvShow)
                startActivity(intent)
            }
        })
        binding.searchRv.adapter = tvShowsAdapter

        binding.inputSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (timer != null) {
                    timer!!.cancel()
                }

            }

            override fun afterTextChanged(s: Editable?) {
                if (!s.toString().trim().isEmpty()) {
                    timer = Timer()
                    timer!!.schedule(object : TimerTask() {
                        override fun run() {
                            Handler(Looper.getMainLooper()).post {
                                currentPage = 1
                                totalAvailablePages = 1
                                searchTVShow(s.toString())
                            }
                        }
                    }, 800)
                } else {
                    tvShows.clear()
                    tvShowsAdapter.notifyDataSetChanged()
                }
            }

        })

        binding.searchRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!binding.searchRv.canScrollVertically(1)) {
                    if (!binding.inputSearch.text.toString().isEmpty()) {
                        binding.progress2.visibility = View.VISIBLE
                        if (currentPage <= totalAvailablePages) {
                            currentPage++
                            searchTVShow(binding.inputSearch.text.toString())
                        }
                    }
                }
            }
        })
        binding.inputSearch.requestFocus()
    }

    private fun searchTVShow(query: String) {
        viewModel.searchTVShow(query, currentPage)
            .observe(this, Observer { tvShowsResponse ->
                binding.progress1.visibility = View.GONE
                binding.progress2.visibility = View.GONE
                if (tvShowsResponse != null) {
                    totalAvailablePages = tvShowsResponse.totalPages!!
                    if (tvShowsResponse.tvShows != null) {
                        val oldCount = tvShows.size
                        tvShows.addAll(tvShowsResponse.tvShows!!)
                        tvShowsAdapter.notifyItemRangeInserted(oldCount, tvShows.size)
                    }
                }
            })
    }

}