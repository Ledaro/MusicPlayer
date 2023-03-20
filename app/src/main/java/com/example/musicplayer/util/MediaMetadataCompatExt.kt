package com.example.musicplayer.util

import android.support.v4.media.MediaMetadataCompat
import com.example.musicplayer.data.entities.SongNew

fun MediaMetadataCompat.toSong(): SongNew? {
    return description?.let {
        SongNew(
            it.mediaId ?: "",
            it.title.toString(),
            it.subtitle.toString(),
            it.mediaUri.toString(),
            it.iconUri.toString()
        )
    }
}
