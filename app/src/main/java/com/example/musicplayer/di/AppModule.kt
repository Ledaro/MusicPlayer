package com.example.musicplayer.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.musicplayer.R
import com.example.musicplayer.exoplayer.MusicServiceConnection
import com.example.musicplayer.ui.albums.song.SwipeSongsAdapterNew
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesMusicServiceConnection(
        @ApplicationContext context: Context
    ) = MusicServiceConnection(context)

    @Singleton
    @Provides
    fun provideSwipeSongAdapter() = SwipeSongsAdapterNew()

    @Singleton
    @Provides
    fun provideGlideInstance(
        @ApplicationContext context: Context
    ) = Glide.with(context).setDefaultRequestOptions(
        RequestOptions()
            .placeholder(R.drawable.ic_image)
            .error(R.drawable.ic_image)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
    )
}
