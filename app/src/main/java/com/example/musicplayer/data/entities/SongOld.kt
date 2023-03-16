package com.example.musicplayer.data.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SongOld(
    var album: String = "",
    var duration: String = "",
    var id: String = "",
    var imageUrl: String = "",
    var isPlaying: Boolean = false,
    var songUrl: String = "",
    var subtitle: String = "",
    var title: String = "",
    var trackNumber: Int = 1

) : Parcelable
