package com.example.musicplayer.ui.albums

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.data.Album
import com.example.musicplayer.databinding.ItemAlbumBinding
import com.google.android.material.card.MaterialCardView

class AlbumsAdapter/*(private val listener:OnItemClickListener)*/:
    ListAdapter<Album, AlbumsAdapter.AlbumsViewHolder>(DiffCallback()) {

    lateinit var onItemClickListener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        AlbumsViewHolder(
            ItemAlbumBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: AlbumsViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class AlbumsViewHolder(private val binding: ItemAlbumBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(album: Album) {
            binding.album = album
            binding.albumCardView.transitionName = album.id.toString()
            binding.albumCardView.setOnClickListener {
                onItemClickListener.onItemClick(album.id, binding.albumCardView)
            }
        }
    }

    interface OnItemClickListener{
        fun onItemClick(albumId: Int, cardView: CardView)
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

