package uz.mobilestudio.tvshows.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import uz.mobilestudio.tvshows.R
import uz.mobilestudio.tvshows.databinding.ItemContainerTvShowBinding
import uz.mobilestudio.tvshows.models.TVShow
import java.lang.Exception

class TVShowsAdapter(var list: List<TVShow>) : RecyclerView.Adapter<TVShowsAdapter.Vh>() {

    inner class Vh(var itemContainerTvShowBinding: ItemContainerTvShowBinding) :
        RecyclerView.ViewHolder(itemContainerTvShowBinding.root) {
        fun onBind(tvShow: TVShow) {
//            Picasso.get().load(tvShow.thumbnail).noFade().into(itemContainerTvShowBinding.imageTVShow,object : Callback{
//                override fun onSuccess() {
//                    itemContainerTvShowBinding.imageTVShow.animate().setDuration(300).alpha(1f).start()
//                }
//
//                override fun onError(e: Exception?) {
//
//                }
//            })
            itemContainerTvShowBinding.tvShow = tvShow
            itemContainerTvShowBinding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
//        return Vh(
//            ItemContainerTvShowBinding.inflate(
//                LayoutInflater.from(parent.context),
//                parent,
//                false
//            )
//        )
        return Vh(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_container_tv_show,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}