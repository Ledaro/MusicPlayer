package com.example.musicplayer.ui.albums.song

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.data.entities.SongNew

abstract class BaseSongAdapter(
    private val layoutId: Int
) :
    RecyclerView.Adapter<BaseSongAdapter.SongsViewHolderNew>() {

    class SongsViewHolderNew(itemView: View) : RecyclerView.ViewHolder(itemView)

    protected val diffCallback = object : DiffUtil.ItemCallback<SongNew>() {
        override fun areItemsTheSame(oldItem: SongNew, newItem: SongNew): Boolean {
            return oldItem.mediaId == newItem.mediaId
        }

        override fun areContentsTheSame(oldItem: SongNew, newItem: SongNew): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    protected abstract val differ: AsyncListDiffer<SongNew>

    var songs: List<SongNew>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SongsViewHolderNew {
        return SongsViewHolderNew(
            LayoutInflater.from(parent.context).inflate(
                layoutId,
                parent,
                false
            )
        )
    }

    protected var onItemClickListener: ((SongNew) -> Unit)? = null

    fun setItemClickListener(listener: (SongNew) -> Unit) {
        onItemClickListener = listener
    }

    override fun getItemCount(): Int {
        return songs.size
    }
}
