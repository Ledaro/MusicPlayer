package com.example.musicplayer.ui.albums

import androidx.lifecycle.ViewModel
import com.example.musicplayer.data.Album


class AlbumsViewModel(
): ViewModel() {

    val KillemAll = Album(
        id = 1,
        title = "Kill'em All",
        artist = "Metallica",
        image = "https://m.media-amazon.com/images/I/61WY2mZd4aL._AC_SL1400_.jpg"
    )

    val RideTheLightning = Album(
        id = 2,
        title = "Ride the Lightning",
        artist = "Metallica",
        image = "https://m.media-amazon.com/images/I/71cr-UivXLL._AC_SL1400_.jpg"
    )

    val MasterOfPuppets = Album(
        id = 3,
        title = "Master of Puppets",
        artist = "Metallica",
        image = "https://m.media-amazon.com/images/I/71sK5UnvqEL._AC_SL1425_.jpg"
    )

    val AndJusticeForAll = Album(
        id = 4,
        title = "And Justice For All",
        artist = "Metallica",
        image = "https://m.media-amazon.com/images/I/81r3FVfNG3L._AC_SL1425_.jpg"
    )

    val BlackAlbum = Album(
        id = 5,
        title = "Metallica",
        artist = "Metallica",
        image = "https://m.media-amazon.com/images/I/411pMy47xvS._AC_SL1400_.jpg"
    )

    val albums = listOf(KillemAll, RideTheLightning, MasterOfPuppets, AndJusticeForAll, BlackAlbum)


}