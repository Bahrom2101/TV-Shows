package uz.mobilestudio.tvshows.responses

import com.google.gson.annotations.SerializedName
import uz.mobilestudio.tvshows.models.TVShow
import uz.mobilestudio.tvshows.models.TVShowDetails

class TVShowDetailsResponse {

    @SerializedName("tvShow")
    var tvShowDetails:TVShowDetails? = null

    @SerializedName("pages")
    var totalPages:Int? = null

    @SerializedName("tv_shows")
    var tvShows:List<TVShow>? = null

}