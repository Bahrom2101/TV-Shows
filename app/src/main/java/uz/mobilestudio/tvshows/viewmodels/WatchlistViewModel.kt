package uz.mobilestudio.tvshows.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.Flowable
import uz.mobilestudio.tvshows.database.TVShowsDatabase
import uz.mobilestudio.tvshows.models.TVShow

class WatchlistViewModel(application: Application) : AndroidViewModel(application) {

    private val tvShowsDatabase = TVShowsDatabase.getInstance(application.applicationContext)

    fun loadWatchlist() : Flowable<List<TVShow>>{
        return tvShowsDatabase.tvShowDao().getWatchList()
    }

    fun removeTVShowFromWatchlist(tvShow: TVShow): Completable {
        return tvShowsDatabase.tvShowDao().removeFromWatchList(tvShow)
    }

}