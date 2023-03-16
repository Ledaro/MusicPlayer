package com.example.musicplayer.ui.albums

import androidx.lifecycle.ViewModel
import com.example.musicplayer.data.entities.Album
import com.example.musicplayer.data.entities.`Song(old)`

class AlbumsViewModel : ViewModel() {

    private val KillemAll = Album(
        id = 1,
        title = "Kill'em All",
        artist = "Metallica",
        image = "https://m.media-amazon.com/images/I/61WY2mZd4aL._AC_SL1400_.jpg",
        duration = "54:12"
    )

    private val RideTheLightning = Album(
        id = 2,
        title = "Ride the Lightning",
        artist = "Metallica",
        image = "https://m.media-amazon.com/images/I/71cr-UivXLL._AC_SL1400_.jpg",
        duration = "45:15"
    )

    private val MasterOfPuppets = Album(
        id = 3,
        title = "Master of Puppets",
        artist = "Metallica",
        image = "https://m.media-amazon.com/images/I/71sK5UnvqEL._AC_SL1425_.jpg",
        duration = "47:57"
    )

    private val AndJusticeForAll = Album(
        id = 4,
        title = "And Justice For All",
        artist = "Metallica",
        image = "https://m.media-amazon.com/images/I/81r3FVfNG3L._AC_SL1425_.jpg",
        duration = "39:45"
    )

    private val BlackAlbum = Album(
        id = 5,
        title = "Metallica",
        artist = "Metallica",
        image = "https://m.media-amazon.com/images/I/411pMy47xvS._AC_SL1400_.jpg",
        duration = "65:12"
    )

    private val Song = com.example.musicplayer.data.entities.`Song(old)`(

    )
/*    private val Song_1 = Song(
        id = 1,
        album = "Master of Puppets",
        title = "Song_1",
        duration = "1:11",
        isPlaying = false

    )

    private val Song_2 = Song(
        id = 2,
        album = "Master of Puppets",
        title = "Song_2",
        duration = "2:22",
        isPlaying = false

    )

    private val Song_3 = Song(
        id = 3,
        album = "Kill'em All",
        title = "Song_3",
        duration = "3:33",
        isPlaying = false

    )

    private val Song_4 = Song(
        id = 4,
        album = "Metallica",
        title = "Song_4",
        duration = "4:44",
        isPlaying = false

    )

    private val Song_5 = Song(
        id = 5,
        album = "And Justice for All",
        title = "Song_5",
        duration = "5:55",
        isPlaying = true

    )*/

    val songsList = emptyList<`Song(old)`>()
    var isGridView = true
    var isListSorted = false

    fun toggleGridView() {
        isGridView = !isGridView
    }

    fun toggleListSorting() {
        isListSorted = !isListSorted
    }

    var albums =
        mutableListOf(KillemAll, RideTheLightning, MasterOfPuppets, AndJusticeForAll, BlackAlbum)

    var albumsSorted = albums.sortedBy { it.title }
}
