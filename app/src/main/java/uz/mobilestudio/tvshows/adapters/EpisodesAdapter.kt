package uz.mobilestudio.tvshows.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.mobilestudio.tvshows.databinding.ItemContainerEpisodesBinding
import uz.mobilestudio.tvshows.models.Episode

class EpisodesAdapter(var list: List<Episode>) : RecyclerView.Adapter<EpisodesAdapter.Vh>() {

    inner class Vh(var item: ItemContainerEpisodesBinding) :
        RecyclerView.ViewHolder(item.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(episode: Episode) {
            var title = "S"
            var season = episode.season
            if (season?.length == 1) {
                season = "0$season"
            }
            var episodeNumber: String = episode.episode!!
            if (episodeNumber.length == 1) {
                episodeNumber = "0$episodeNumber"
            }
            episodeNumber = "E$episodeNumber"
            title = "$title$season$episodeNumber"
            item.title.text = title
            item.name.text = episode.name
            item.date.text = "Air date: ${episode.airDate}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(
            ItemContainerEpisodesBinding.inflate(
                LayoutInflater.from(parent.context),
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