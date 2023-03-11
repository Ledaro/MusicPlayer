package com.example.musicplayer.ui.albums.album

import android.animation.LayoutTransition
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionManager
import com.example.musicplayer.R
import com.example.musicplayer.data.Song
import com.example.musicplayer.databinding.FragmentAlbumBinding
import com.example.musicplayer.ui.albums.AlbumsViewModel
import com.example.musicplayer.util.themeColor
import com.google.android.material.transition.MaterialArcMotion
import com.google.android.material.transition.MaterialContainerTransform

class AlbumFragment : Fragment(R.layout.fragment_album), SongsAdapter.OnItemClickListener {
    private val args: AlbumFragmentArgs by navArgs()
    private val viewModel: AlbumsViewModel by viewModels()
    private lateinit var binding: FragmentAlbumBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        sharedElementEnterTransition = containerTransformEnterTransition(true)
        sharedElementReturnTransition = containerTransformEnterTransition(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_album, container, false)
        (activity as AppCompatActivity).setSupportActionBar(binding.albumToolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.apply {
            albumFab.setOnClickListener {
                TransitionManager.beginDelayedTransition(
                    container!!, containerTransformFabToMusicPlayerTransform(
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

            albumMusicPlayerContainer.setOnClickListener {
                TransitionManager.beginDelayedTransition(
                    container!!,
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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.transitionName = args.album.id.toString()
        val album = viewModel.albums.find { it.id == args.album.id }
        val songsAdapter = SongsAdapter(this)
        binding.album = album

        binding.songsRecyclerview.apply {
            adapter = songsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            setHasFixedSize(true)
        }

        songsAdapter.submitList(filterSongsByAlbum(viewModel.songsList, album!!.title))
    }

    private fun filterSongsByAlbum(songs: List<Song>, albumName: String): List<Song> {
        return songs.filter { it.album.lowercase() == albumName.lowercase() }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment_album, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            activity?.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
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
}
