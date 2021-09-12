package uz.mobilestudio.tvshows.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        tvShows = ArrayList()

        doInitialization()
    }

    private fun doInitialization() {
        binding.tvShowsRecyclerView.setHasFixedSize(true)
        viewModel = ViewModelProvider(this).get(MostPopularTVShowsViewModel::class.java)
        tvShowsAdapter = TVShowsAdapter(tvShows)
        binding.tvShowsRecyclerView.adapter = tvShowsAdapter
        getMostPopularTVShows()

    }

    private fun getMostPopularTVShows() {
        binding.isLoading = true
        viewModel.getMostPopularTVShows(0).observe(this, Observer { mostPopularTVShowsResponse ->
            binding.isLoading = false
            if (mostPopularTVShowsResponse != null) {
                if (mostPopularTVShowsResponse.tvShows != null) {
                    tvShows.clear()
                    tvShows.addAll(mostPopularTVShowsResponse.tvShows!!)
                    tvShowsAdapter.notifyDataSetChanged()
                }
            }
        })
    }
}