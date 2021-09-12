package uz.mobilestudio.tvshows.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import uz.mobilestudio.tvshows.R
import uz.mobilestudio.tvshows.adapters.TVShowsAdapter
import uz.mobilestudio.tvshows.databinding.ActivityMainBinding
import uz.mobilestudio.tvshows.models.TVShow
import uz.mobilestudio.tvshows.viewmodels.MostPopularTVShowsViewModel

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MostPopularTVShowsViewModel
    lateinit var tvShowsAdapter: TVShowsAdapter
    lateinit var tvShows: ArrayList<TVShow>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tvShows = ArrayList()

        doInitialization()
    }

    private fun doInitialization() {
        binding.tvShowsRecyclerView.setHasFixedSize(true)
        viewModel = ViewModelProvider(this).get(MostPopularTVShowsViewModel::class.java)
        getMostPopularTVShows()
    }

    private fun getMostPopularTVShows() {
        binding.progress.visibility = View.VISIBLE
        viewModel.getMostPopularTVShows(0).observe(this, Observer { mostPopularTVShowsResponse ->
            if (mostPopularTVShowsResponse != null) {
                if (mostPopularTVShowsResponse.tvShows != null) {
                    binding.progress.visibility = View.GONE
                    tvShows.clear()
                    tvShows.addAll(mostPopularTVShowsResponse.tvShows!!)
                    tvShowsAdapter = TVShowsAdapter(tvShows)
                    binding.tvShowsRecyclerView.adapter = tvShowsAdapter
                }
            }
        })
    }
}