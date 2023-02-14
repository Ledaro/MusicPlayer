package com.example.musicplayer.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Album(
    var id: Int,
    var title: String,
    var artist: String,
    var image: String
) : Parcelable
