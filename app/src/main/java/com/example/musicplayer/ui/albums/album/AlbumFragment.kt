package com.example.musicplayer.ui.albums.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.musicplayer.R
import com.example.musicplayer.databinding.FragmentAlbumBinding
import com.example.musicplayer.ui.albums.AlbumsViewModel

class AlbumFragment:Fragment(R.layout.fragment_album) {
    private val args: AlbumFragmentArgs by navArgs()
    private lateinit var binding: FragmentAlbumBinding
    private val viewModel: AlbumsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_album, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.transitionName = args.albumId.toString()
        val album = viewModel.albums.find { it.id == args.albumId }
        binding.album = album
    }
}