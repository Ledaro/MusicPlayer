package com.example.musicplayer.ui.albums

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.musicplayer.data.Album
import com.example.musicplayer.databinding.GridItemAlbumBinding
import com.example.musicplayer.databinding.ListItemAlbumBinding

class AlbumsAdapter(private val listener: OnItemClickListener, private val asGrid: Boolean) :
    ListAdapter<Album, AlbumsAdapter.BaseViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (asGrid) {
            GridViewHolder(GridItemAlbumBinding.inflate(inflater, parent, false))
        } else {
            ListViewHolder(ListItemAlbumBinding.inflate(inflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    abstract class BaseViewHolder(binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        abstract fun bind(album: Album)
    }

    inner class GridViewHolder(private val binding: GridItemAlbumBinding) :
        BaseViewHolder(binding) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val album = getItem(position)
                    listener.onItemClick(album, binding.albumItemContainer)
                }
            }
        }

        override fun bind(album: Album) {
            binding.album = album
            binding.albumItemContainer.transitionName = album.id.toString()
        }
    }

    inner class ListViewHolder(private val binding: ListItemAlbumBinding) :
        BaseViewHolder(binding) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val album = getItem(position)
                    listener.onItemClick(album, binding.albumItemContainer)
                }
            }
        }

        override fun bind(album: Album) {
            binding.album = album
            binding.albumItemContainer.transitionName = album.id.toString()
        }
    }

    interface OnItemClickListener {
        fun onItemClick(album: Album, cardView: CardView)
    }

    companion object {
        class DiffCallback : DiffUtil.ItemCallback<Album>() {
            override fun areItemsTheSame(oldItem: Album, newItem: Album) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Album, newItem: Album) =
                oldItem == newItem
        }
    }
}
