package uz.mobilestudio.tvshows.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import uz.mobilestudio.tvshows.responses.TVShowDetailsResponse
import uz.mobilestudio.tvshows.responses.TVShowsResponse

interface ApiService {

    @GET("most-popular")
    fun getMostPopularTVShows(@Query("page") page:Int):Call<TVShowsResponse>

    @GET("show-details")
    fun getTVShowDetails(@Query("q") tvShowId:String):Call<TVShowDetailsResponse>

    @GET("search")
    fun searchTVShow(@Query("q") query:String,@Query("page") page: Int):Call<TVShowsResponse>
}