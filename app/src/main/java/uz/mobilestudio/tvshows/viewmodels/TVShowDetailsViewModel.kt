package uz.mobilestudio.tvshows.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Completable
import uz.mobilestudio.tvshows.database.TVShowsDatabase
import uz.mobilestudio.tvshows.models.TVShow
import uz.mobilestudio.tvshows.models.TVShowDetails
import uz.mobilestudio.tvshows.repositories.MostPopularTVShowsRepository
import uz.mobilestudio.tvshows.repositories.TVShowDetailsRepository
import uz.mobilestudio.tvshows.responses.TVShowDetailsResponse
import uz.mobilestudio.tvshows.responses.TVShowsResponse

class TVShowDetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val tvShowDetailsRepository = TVShowDetailsRepository()
    private val tvShowsDatabase = TVShowsDatabase.getInstance(application.applicationContext)

    fun getTVShowDetails(tvShowId: String): LiveData<TVShowDetailsResponse> {
        return tvShowDetailsRepository.getTVShowDetails(tvShowId)
    }

    fun addToWatchList(tvShow: TVShow) : Completable {
        return tvShowsDatabase.tvShowDao().addWatchList(tvShow)
    }

}