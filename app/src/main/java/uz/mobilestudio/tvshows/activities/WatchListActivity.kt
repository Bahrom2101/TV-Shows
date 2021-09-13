package uz.mobilestudio.tvshows.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import uz.mobilestudio.tvshows.adapters.WatchlistAdapter
import uz.mobilestudio.tvshows.database.TVShowsDatabase
import uz.mobilestudio.tvshows.databinding.ActivityWatchListBinding
import uz.mobilestudio.tvshows.listeners.WatchlistListener
import uz.mobilestudio.tvshows.models.TVShow
import uz.mobilestudio.tvshows.viewmodels.WatchlistViewModel

class WatchListActivity : AppCompatActivity() {

    lateinit var binding: ActivityWatchListBinding
    lateinit var viewModel: WatchlistViewModel
    lateinit var watchlistAdapter: WatchlistAdapter
    lateinit var watchlist: ArrayList<TVShow>
    lateinit var tvShowsDatabase: TVShowsDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWatchListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        doInitialization()

    }

    private fun doInitialization() {
        viewModel = ViewModelProvider(this).get(WatchlistViewModel::class.java)
        binding.imageBack.setOnClickListener { onBackPressed() }
        watchlist = ArrayList()
        tvShowsDatabase = TVShowsDatabase.getInstance(applicationContext)
    }

    private fun loadWatchlist() {
        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(
            viewModel.loadWatchlist().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    binding.progress.visibility = View.GONE
                    if (watchlist.size > 0) {
                        watchlist.clear()
                    }
                    watchlist.addAll(it)
                    watchlistAdapter = WatchlistAdapter(watchlist, object : WatchlistListener {
                        override fun onTVShowClicked(tvShow: TVShow) {
                            val intent =
                                Intent(this@WatchListActivity, TVShowDetailsActivity::class.java)
                            intent.putExtra("tvShow", tvShow)
                            startActivity(intent)
                        }

                        override fun removeTVShowFromWatchlist(tvShow: TVShow, position: Int) {
                            val compositeDisposableForDelete = CompositeDisposable()
                            compositeDisposableForDelete.add(viewModel.removeTVShowFromWatchlist(
                                tvShow
                            )
                                .subscribeOn(Schedulers.computation())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe {
                                    watchlist.remove(tvShow)
                                    watchlistAdapter.notifyItemRemoved(position)
                                    watchlistAdapter.notifyItemRangeChanged(position,watchlistAdapter.itemCount)
                                    compositeDisposableForDelete.dispose()
                                })
                        }
                    })
                    binding.watchlistRv.visibility = View.VISIBLE
                    binding.watchlistRv.adapter = watchlistAdapter
                    compositeDisposable.dispose()
                }
        )
    }

    override fun onResume() {
        super.onResume()
        loadWatchlist()
    }
}