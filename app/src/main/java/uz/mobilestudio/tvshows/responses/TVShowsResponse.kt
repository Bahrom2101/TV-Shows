package uz.mobilestudio.tvshows.responses

import com.google.gson.annotations.SerializedName
import uz.mobilestudio.tvshows.models.TVShow

class TVShowsResponse {

    @SerializedName("page")
    var page:Int? = null

    @SerializedName("pages")
    var totalPages:Int? = null

    @SerializedName("tv_shows")
    var tvShows:List<TVShow>? = null

}