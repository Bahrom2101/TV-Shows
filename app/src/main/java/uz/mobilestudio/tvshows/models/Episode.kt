package uz.mobilestudio.tvshows.models

import com.google.gson.annotations.SerializedName

class Episode {

    @SerializedName("season")
    var season:String? = null

    @SerializedName("episode")
    var episode:String? = null

    @SerializedName("name")
    var name:String? = null

    @SerializedName("air_date")
    var airDate:String? = null

}