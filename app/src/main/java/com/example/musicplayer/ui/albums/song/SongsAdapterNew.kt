package com.example.musicplayer.ui.albums.song

import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import com.bumptech.glide.RequestManager
import com.example.musicplayer.R
import javax.inject.Inject

class SongsAdapterNew @Inject constructor(
    private val glide: RequestManager
) : BaseSongAdapter(R.layout.item_song_new_2) {

    override val differ = AsyncListDiffer(this, diffCallback)

    override fun onBindViewHolder(holder: SongsViewHolderNew, position: Int) {
        val song = songs[position]
        val itemView = holder.itemView // No need to cast the itemView to a different type
        itemView.apply {
            // Access views in the layout using their IDs
            val tvPrimary = findViewById<TextView>(R.id.tvPrimary)
            val tvSecondary = findViewById<TextView>(R.id.tvSecondary)
            val ivItemImage = findViewById<ImageView>(R.id.ivItemImage)

            tvPrimary.text = song.title
            tvSecondary.text = song.subtitle
            glide.load(song.imageUrl).into(ivItemImage)

            setOnClickListener {
                onItemClickListener?.let { click ->
                    click(song)
                }
            }
        }
    }
}
