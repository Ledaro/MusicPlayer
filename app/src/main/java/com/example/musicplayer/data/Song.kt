package com.example.musicplayer.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Song(
    var id: Int,
    var title: String,
    var duration: String,
    var isPlaying: Boolean
) : Parcelable
