package com.example.musicplayer.ui.albums.album

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.R
import com.example.musicplayer.databinding.FragmentAlbumNewBinding
import com.example.musicplayer.other.Status
import com.example.musicplayer.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AlbumFragmentNew : Fragment(R.layout.fragment_album_new) {

    lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var songAdapter: SongsAdapterNew

    private var _binding: FragmentAlbumNewBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAlbumNewBinding.bind(view)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        songAdapter.setOnItemClickListener {
            mainViewModel.playOrToggleSong(it)
        }
    }

    private fun setupRecyclerView() = binding.rvAllSongs.apply {
        adapter = songAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }

    private fun subscribeToObservers() {
        mainViewModel.mediaItems.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Status.SUCCESS -> {
                    binding.allSongsProgressBar.isVisible = false
                    result.data?.let { songs ->
                        songAdapter.songNews = songs
                    }
                }
                Status.ERROR -> Unit
                Status.LOADING -> binding.allSongsProgressBar.isVisible = true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
