package uz.mobilestudio.tvshows.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.mobilestudio.tvshows.network.ApiClient
import uz.mobilestudio.tvshows.network.ApiService
import uz.mobilestudio.tvshows.responses.TVShowDetailsResponse
import uz.mobilestudio.tvshows.responses.TVShowsResponse

class SearchTVShowRepository {

    private var apiService: ApiService = ApiClient.getRetrofit().create(ApiService::class.java)

    fun searchTVShow(query:String,page:Int): LiveData<TVShowsResponse> {
        val data = MutableLiveData<TVShowsResponse>()
        apiService.searchTVShow(query, page).enqueue(object : Callback<TVShowsResponse> {
            override fun onResponse(
                call: Call<TVShowsResponse>,
                response: Response<TVShowsResponse>
            ) {
                data.value = response.body()
            }

            override fun onFailure(call: Call<TVShowsResponse>, t: Throwable) {
                data.value = null
            }
        })
        return data
    }
}