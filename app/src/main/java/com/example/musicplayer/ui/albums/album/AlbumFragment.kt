package com.example.musicplayer.ui.albums.album

import android.animation.LayoutTransition
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionManager
import com.example.musicplayer.R
import com.example.musicplayer.data.entities.Song
import com.example.musicplayer.databinding.FragmentAlbumBinding
import com.example.musicplayer.ui.albums.AlbumsViewModel
import com.example.musicplayer.util.themeColor
import com.google.android.material.transition.MaterialArcMotion
import com.google.android.material.transition.MaterialContainerTransform

class AlbumFragment : Fragment(R.layout.fragment_album), SongsAdapter.OnItemClickListener {
    private val args: AlbumFragmentArgs by navArgs()
    private val viewModel: AlbumsViewModel by viewModels()
    private var _binding: FragmentAlbumBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition = containerTransformEnterTransition(true)
        sharedElementReturnTransition = containerTransformEnterTransition(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAlbumBinding.bind(view)

        val songsAdapter = SongsAdapter(this)
        val album = viewModel.albums.find { it.id == args.album.id }

        binding.apply {
            root.transitionName = args.album.id.toString()

            binding.album = album

            albumFab.setOnClickListener {
                onFabClick()
            }

            albumMusicPlayerContainer.setOnClickListener {
                onMusicPlayerClick()
            }

            songsRecyclerview.apply {
                adapter = songsAdapter
                layoutManager = LinearLayoutManager(requireContext())
                addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
                setHasFixedSize(true)
            }

            albumToolbar.apply {
                setNavigationOnClickListener {
                    activity?.onBackPressed()
                }

                setOnClickListener {

                }
            }
        }

        songsAdapter.submitList(filterSongsByAlbum(viewModel.songsList, album!!.title))
    }

    private fun filterSongsByAlbum(songs: List<Song>, albumName: String): List<Song> {
        return songs.filter { it.album.lowercase() == albumName.lowercase() }
    }

    private fun containerTransformFabToMusicPlayerTransform(
        startView: View, endView: View, entering: Boolean,
    ) = MaterialContainerTransform(requireContext(), entering).apply {
        setPathMotion(MaterialArcMotion())
        scrimColor = Color.TRANSPARENT
        this.startView = startView
        this.endView = endView
        addTarget(endView)
    }

    private fun containerTransformEnterTransition(entering: Boolean) =
        MaterialContainerTransform(requireContext(), entering).apply {
            drawingViewId = R.id.nav_host_fragment
            duration = 300
            scrimColor = Color.TRANSPARENT
            setAllContainerColors(requireContext().themeColor(com.google.android.material.R.attr.colorSurface))
        }

    override fun onItemClick(song: Song) {
        TODO("Not yet implemented")
    }

    private fun onFabClick() {
        binding.apply {
            TransitionManager.beginDelayedTransition(
                binding.root as ViewGroup, containerTransformFabToMusicPlayerTransform(
                    albumFab,
                    albumMusicPlayerContainer,
                    true
                )
            )
            albumFab.visibility = View.GONE
            albumMusicPlayerContainer.visibility = View.VISIBLE
            albumNestedScrollView.layoutTransition
                .enableTransitionType(LayoutTransition.CHANGING)
        }
    }

    private fun onMusicPlayerClick() {
        binding.apply {
            TransitionManager.beginDelayedTransition(
                binding.root as ViewGroup,
                containerTransformFabToMusicPlayerTransform(
                    albumMusicPlayerContainer,
                    albumFab,
                    false
                )
            )
            albumMusicPlayerContainer.visibility = View.GONE
            albumFab.visibility = View.VISIBLE
        }
    }
}
