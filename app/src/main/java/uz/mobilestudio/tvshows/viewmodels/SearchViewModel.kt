package uz.mobilestudio.tvshows.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.Flowable
import uz.mobilestudio.tvshows.database.TVShowsDatabase
import uz.mobilestudio.tvshows.models.TVShow
import uz.mobilestudio.tvshows.repositories.SearchTVShowRepository
import uz.mobilestudio.tvshows.responses.TVShowsResponse

class SearchViewModel : ViewModel() {

    private val searchTVShowsRepository = SearchTVShowRepository()

    fun searchTVShow(query:String,page:Int): LiveData<TVShowsResponse> {
        return searchTVShowsRepository.searchTVShow(query, page)
    }

}