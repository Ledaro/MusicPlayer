package com.example.musicplayer.ui.albums.song

import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import com.example.musicplayer.R

class SwipeSongsAdapterNew : BaseSongAdapter(R.layout.item_swipe_song) {

    override val differ = AsyncListDiffer(this, diffCallback)

    override fun onBindViewHolder(holder: SongsViewHolderNew, position: Int) {
        val song = songs[position]
        val itemView = holder.itemView // No need to cast the itemView to a different type
        itemView.apply {
            val tvPrimary = findViewById<TextView>(R.id.tvPrimary)
            val text = "${song.title} - ${song.subtitle}"

            tvPrimary.text = text

            setOnClickListener {
                onItemClickListener?.let { click ->
                    click(song)
                }
            }
        }
    }
}
