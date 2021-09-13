package uz.mobilestudio.tvshows.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import uz.mobilestudio.tvshows.R
import uz.mobilestudio.tvshows.databinding.ItemContainerTvShowBinding
import uz.mobilestudio.tvshows.listeners.TVShowsListener
import uz.mobilestudio.tvshows.listeners.WatchlistListener
import uz.mobilestudio.tvshows.models.TVShow
import java.lang.Exception

class WatchlistAdapter(var list: List<TVShow>, var watchlistListener: WatchlistListener) :
    RecyclerView.Adapter<WatchlistAdapter.Vh>() {

    inner class Vh(var itemContainerTvShowBinding: ItemContainerTvShowBinding) :
        RecyclerView.ViewHolder(itemContainerTvShowBinding.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(tvShow: TVShow, position: Int) {
            itemContainerTvShowBinding.textName.text = tvShow.name
            itemContainerTvShowBinding.textNetwork.text =
                tvShow.network + " (" + tvShow.country + ")"
            itemContainerTvShowBinding.textStarted.text = "Started on: " + tvShow.startDate
            itemContainerTvShowBinding.textStatus.text = tvShow.status
            Picasso.get().load(tvShow.thumbnail).noFade()
                .into(itemContainerTvShowBinding.imageTVShow, object : Callback {
                    override fun onSuccess() {
                        itemContainerTvShowBinding.imageTVShow.animate().setDuration(300).alpha(1f)
                            .start()
                    }

                    override fun onError(e: Exception?) {

                    }
                })
            itemContainerTvShowBinding.root.setOnClickListener {
                watchlistListener.onTVShowClicked(tvShow)
            }
            itemContainerTvShowBinding.imageDelete.visibility = View.VISIBLE
            itemContainerTvShowBinding.imageDelete.setOnClickListener {
                watchlistListener.removeTVShowFromWatchlist(tvShow, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(
            ItemContainerTvShowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}