package uz.mobilestudio.tvshows.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import uz.mobilestudio.tvshows.databinding.ItemContainerSliderImageBinding
import java.lang.Exception

class ImageSliderAdapter(var list: List<String>) : RecyclerView.Adapter<ImageSliderAdapter.Vh>() {
    inner class Vh(var itemContainerSliderImageBinding: ItemContainerSliderImageBinding) :
        RecyclerView.ViewHolder(itemContainerSliderImageBinding.root) {
        fun onBind(imageURL: String) {
            Picasso.get().load(imageURL).noFade()
                .into(itemContainerSliderImageBinding.image, object :
                    Callback {
                    override fun onSuccess() {
                        itemContainerSliderImageBinding.image.animate().setDuration(300).alpha(1f)
                            .start()
                    }

                    override fun onError(e: Exception?) {

                    }
                })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(
            ItemContainerSliderImageBinding.inflate(
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