package uz.mobilestudio.tvshows.models

import com.google.gson.annotations.SerializedName

class TVShow {

    @SerializedName("id")
    var id:Int? = null

    @SerializedName("name")
    var name:String? = null

    @SerializedName("start_date")
    var startDate:String? = null

    @SerializedName("country")
    var country:String? = null

    @SerializedName("network")
    var network:String? = null

    @SerializedName("status")
    var status:String? = null

    @SerializedName("image_thumbnail_path")
    var thumbnail:String? = null
    override fun toString(): String {
        return "TVShow(id=$id, name=$name, startDate=$startDate, country=$country, network=$network, status=$status, thumbnail=$thumbnail)"
    }

}