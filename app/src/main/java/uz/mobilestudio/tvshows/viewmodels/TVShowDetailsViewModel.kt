package uz.mobilestudio.tvshows.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import uz.mobilestudio.tvshows.models.TVShowDetails
import uz.mobilestudio.tvshows.repositories.MostPopularTVShowsRepository
import uz.mobilestudio.tvshows.repositories.TVShowDetailsRepository
import uz.mobilestudio.tvshows.responses.TVShowDetailsResponse
import uz.mobilestudio.tvshows.responses.TVShowsResponse

class TVShowDetailsViewModel: ViewModel() {

    private val tvShowDetailsRepository = TVShowDetailsRepository()

    fun getTVShowDetails(tvShowId:String):LiveData<TVShowDetailsResponse> {
        return tvShowDetailsRepository.getTVShowDetails(tvShowId)
    }

}