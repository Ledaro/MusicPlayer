package com.example.musicplayer.ui.albums.album

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.data.entities.SongNew
import com.example.musicplayer.databinding.ItemSongBinding

class SongsAdapter(private val listener: OnItemClickListener) :
    ListAdapter<SongNew, SongsAdapter.SongsViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SongsViewHolder(
            ItemSongBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: SongsViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class SongsViewHolder(private val binding: ItemSongBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val song = getItem(position)
                        listener.onItemClick(song)
                    }
                }
            }
        }

        fun bind(songNew: SongNew) {
/*            binding.song = song*/
        }
    }

    interface OnItemClickListener {
        fun onItemClick(songNew: SongNew)
    }

    companion object {
        class DiffCallback : DiffUtil.ItemCallback<SongNew>() {
            override fun areItemsTheSame(oldItem: SongNew, newItem: SongNew) =
                oldItem.mediaId == newItem.mediaId

            override fun areContentsTheSame(oldItem: SongNew, newItem: SongNew) =
                oldItem == newItem
        }
    }
}
