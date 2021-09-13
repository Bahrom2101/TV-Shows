package uz.mobilestudio.tvshows.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import uz.mobilestudio.tvshows.R
import uz.mobilestudio.tvshows.adapters.TVShowsAdapter
import uz.mobilestudio.tvshows.databinding.ActivityMainBinding
import uz.mobilestudio.tvshows.listeners.TVShowsListener
import uz.mobilestudio.tvshows.models.TVShow
import uz.mobilestudio.tvshows.viewmodels.MostPopularTVShowsViewModel

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MostPopularTVShowsViewModel
    lateinit var tvShowsAdapter: TVShowsAdapter
    lateinit var tvShows: ArrayList<TVShow>
    private var currentPage = 1
    private var totalAvailablePages = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progress1.visibility = View.VISIBLE

        tvShows = ArrayList()

        doInitialization()
    }

    private fun doInitialization() {
        binding.tvShowsRecyclerView.setHasFixedSize(true)
        viewModel = ViewModelProvider(this).get(MostPopularTVShowsViewModel::class.java)
        tvShowsAdapter = TVShowsAdapter(tvShows, object : TVShowsListener {
            override fun onTVShowClicked(tvShow: TVShow) {
                val intent = Intent(this@MainActivity, TVShowDetailsActivity::class.java)
                intent.putExtra("tvShow", tvShow)
                startActivity(intent)
            }
        })
        binding.tvShowsRecyclerView.adapter = tvShowsAdapter
        binding.tvShowsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!binding.tvShowsRecyclerView.canScrollVertically(1)) {
                    binding.progress2.visibility = View.VISIBLE
                    if (currentPage <= totalAvailablePages) {
                        currentPage++
                        getMostPopularTVShows()
                    }
                }
            }
        })
        binding.imageWatchlist.setOnClickListener {
            startActivity(Intent(this, WatchListActivity::class.java))
        }
        getMostPopularTVShows()
    }

    private fun getMostPopularTVShows() {
        viewModel.getMostPopularTVShows(currentPage)
            .observe(this, Observer { mostPopularTVShowsResponse ->
                binding.progress1.visibility = View.GONE
                binding.progress2.visibility = View.GONE
                if (mostPopularTVShowsResponse != null) {
                    totalAvailablePages = mostPopularTVShowsResponse.totalPages!!
                    if (mostPopularTVShowsResponse.tvShows != null) {
                        val oldCount = tvShows.size
                        tvShows.addAll(mostPopularTVShowsResponse.tvShows!!)
                        tvShowsAdapter.notifyItemRangeInserted(oldCount, tvShows.size)
                    }
                }
            })
    }
}