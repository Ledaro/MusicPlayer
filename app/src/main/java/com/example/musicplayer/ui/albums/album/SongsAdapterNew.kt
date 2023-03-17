package com.example.musicplayer.ui.albums.album

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.musicplayer.data.entities.SongNew
import com.example.musicplayer.databinding.ItemSongNew2Binding
import javax.inject.Inject

class SongsAdapterNew @Inject constructor(
    private val glide: RequestManager
) :
    RecyclerView.Adapter<SongsAdapterNew.SongsViewHolderNew>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) =
        SongsViewHolderNew(
            ItemSongNew2Binding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: SongsViewHolderNew, position: Int) {
        val currentItem = songNews[position]
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { click ->
                click(currentItem)
            }
        }
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return songNews.size
    }

    inner class SongsViewHolderNew(private val binding: ItemSongNew2Binding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(songNew: SongNew) {
            binding.apply {
                tvPrimary.text = songNew.title
                tvSecondary.text = songNew.subtitle
                glide.load(songNew.imageUrl).into(ivItemImage)

            }
        }
    }

    private var onItemClickListener: ((SongNew) -> Unit)? = null

    fun setOnItemClickListener(listener: (SongNew) -> Unit) {
        onItemClickListener = listener
    }


    private val diffCallback = object : DiffUtil.ItemCallback<SongNew>() {
        override fun areItemsTheSame(oldItem: SongNew, newItem: SongNew): Boolean {
            return oldItem.mediaId == newItem.mediaId
        }

        override fun areContentsTheSame(oldItem: SongNew, newItem: SongNew): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var songNews: List<SongNew>
        get() = differ.currentList
        set(value) = differ.submitList(value)

}
