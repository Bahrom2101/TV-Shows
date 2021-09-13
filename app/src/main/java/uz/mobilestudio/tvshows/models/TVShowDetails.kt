package uz.mobilestudio.tvshows.models

import com.google.gson.annotations.SerializedName

class TVShowDetails {

    @SerializedName("url")
    var url:String? = null

    @SerializedName("description")
    var description:String? = null

    @SerializedName("runtime")
    var runtime:String? = null

    @SerializedName("image_path")
    var imagePath:String? = null

    @SerializedName("rating")
    var rating:String? = null

    @SerializedName("genres")
    var genres:List<String>? = null

    @SerializedName("pictures")
    var pictures:List<String>? = null

    @SerializedName("episodes")
    var episodes:List<Episode>? = null
}