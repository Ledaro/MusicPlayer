package com.example.musicplayer.ui.albums

import androidx.lifecycle.ViewModel
import com.example.musicplayer.data.Album
import com.example.musicplayer.data.Song


class AlbumsViewModel : ViewModel() {

    private val Song_1 = Song(
        id = 1,
        title = "Song_1",
        duration = "1:11",
        isPlaying = false

    )

    private val Song_2 = Song(
        id = 2,
        title = "Song_2",
        duration = "2:22",
        isPlaying = false

    )

    private val Song_3 = Song(
        id = 3,
        title = "Song_3",
        duration = "3:33",
        isPlaying = false

    )

    private val Song_4 = Song(
        id = 4,
        title = "Song_4",
        duration = "4:44",
        isPlaying = false

    )

    private val Song_5 = Song(
        id = 5,
        title = "Song_5",
        duration = "5:55",
        isPlaying = false

    )

    private val KillemAll by lazy {
        Album(
        id = 1,
        title = "Kill'em All",
        artist = "Metallica",
        image = "https://m.media-amazon.com/images/I/61WY2mZd4aL._AC_SL1400_.jpg",
        tracks = songsList
    )
    }

    private val RideTheLightning by lazy {
        Album(
        id = 2,
        title = "Ride the Lightning",
        artist = "Metallica",
        image = "https://m.media-amazon.com/images/I/71cr-UivXLL._AC_SL1400_.jpg",
        tracks = songsList
    )
    }

    private val MasterOfPuppets by lazy {
        Album(
        id = 3,
        title = "Master of Puppets",
        artist = "Metallica",
        image = "https://m.media-amazon.com/images/I/71sK5UnvqEL._AC_SL1425_.jpg",
        tracks = songsList
    )
    }

    private val AndJusticeForAll by lazy {
        Album(
        id = 4,
        title = "And Justice For All",
        artist = "Metallica",
        image = "https://m.media-amazon.com/images/I/81r3FVfNG3L._AC_SL1425_.jpg",
        tracks = songsList
    )
    }

    private val BlackAlbum by lazy {
        Album(
        id = 5,
        title = "Metallica",
        artist = "Metallica",
        image = "https://m.media-amazon.com/images/I/411pMy47xvS._AC_SL1400_.jpg",
        tracks = songsList
    )
    }

    private val songsList = listOf(Song_1,Song_2,Song_3,Song_4,Song_5)

    val albums = listOf(KillemAll, RideTheLightning, MasterOfPuppets, AndJusticeForAll, BlackAlbum)
}
