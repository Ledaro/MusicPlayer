package com.example.musicplayer.data.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SongNew(
    val mediaId: String = "",
    val title: String = "",
    val subtitle: String = "",
    val songUrl: String = "",
    val imageUrl: String = ""

) : Parcelable
