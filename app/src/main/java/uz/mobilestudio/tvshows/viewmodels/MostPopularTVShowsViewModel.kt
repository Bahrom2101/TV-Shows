package uz.mobilestudio.tvshows.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import uz.mobilestudio.tvshows.repositories.MostPopularTVShowsRepository
import uz.mobilestudio.tvshows.responses.TVShowsResponse

class MostPopularTVShowsViewModel: ViewModel() {

    private val mostPopularTVShowsRepository = MostPopularTVShowsRepository()

    fun getMostPopularTVShows(page:Int):LiveData<TVShowsResponse> {
        return mostPopularTVShowsRepository.getMostPopularTVShows(page)
    }

}