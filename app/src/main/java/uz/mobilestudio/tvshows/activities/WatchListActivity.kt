package uz.mobilestudio.tvshows.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import uz.mobilestudio.tvshows.databinding.ActivityWatchListBinding
import uz.mobilestudio.tvshows.viewmodels.WatchlistViewModel

class WatchListActivity : AppCompatActivity() {

    lateinit var binding: ActivityWatchListBinding
    lateinit var viewModel: WatchlistViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWatchListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        doInitialization()

    }

    private fun doInitialization() {
        viewModel = ViewModelProvider(this).get(WatchlistViewModel::class.java)
        binding.imageBack.setOnClickListener { onBackPressed() }

    }

    private fun loadWatchlist() {
        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(
            viewModel.loadWatchlist().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    binding.progress.visibility = View.GONE
                    Toast.makeText(applicationContext, "Watchlist: " + it.size, Toast.LENGTH_SHORT)
                        .show()
                }
        )
    }

    override fun onResume() {
        super.onResume()
        loadWatchlist()
    }
}