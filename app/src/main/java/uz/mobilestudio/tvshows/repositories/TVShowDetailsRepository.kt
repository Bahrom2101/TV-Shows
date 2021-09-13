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

class TVShowDetailsRepository {

    private var apiService: ApiService = ApiClient.getRetrofit().create(ApiService::class.java)

    fun getTVShowDetails(tvShowId:String):LiveData<TVShowDetailsResponse> {
        val data = MutableLiveData<TVShowDetailsResponse>()
        apiService.getTVShowDetails(tvShowId).enqueue(object : Callback<TVShowDetailsResponse>{
            override fun onResponse(
                call: Call<TVShowDetailsResponse>,
                response: Response<TVShowDetailsResponse>
            ) {
                data.value = response.body()
            }

            override fun onFailure(call: Call<TVShowDetailsResponse>, t: Throwable) {
                data.value = null
            }
        })
        return data
    }
}