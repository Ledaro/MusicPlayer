package com.example.musicplayer.data.remote

import com.example.musicplayer.data.entities.SongNew
import com.example.musicplayer.other.Constants.SONG_COLLECTION
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class MusicDatabase {

    private val firestore = FirebaseFirestore.getInstance()
    private val songCollection = firestore.collection(SONG_COLLECTION)

    suspend fun getAllSongs(): List<SongNew> {
        return try {
            songCollection.get().await().toObjects(SongNew::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }
}
