package com.example.musicplayer.ui.albums.album

import android.animation.LayoutTransition
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginTop
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionManager
import com.example.musicplayer.R
import com.example.musicplayer.databinding.FragmentAlbumBinding
import com.example.musicplayer.ui.albums.AlbumsViewModel
import com.example.musicplayer.util.themeColor
import com.google.android.material.color.MaterialColors
import com.google.android.material.transition.MaterialArcMotion
import com.google.android.material.transition.MaterialContainerTransform


class AlbumFragment : Fragment(R.layout.fragment_album) {
    private val args: AlbumFragmentArgs by navArgs()
    private val viewModel: AlbumsViewModel by viewModels()
    private lateinit var binding: FragmentAlbumBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_host_fragment
            duration = 300
            scrimColor = Color.TRANSPARENT
            setAllContainerColors(requireContext().themeColor(com.google.android.material.R.attr.colorSurface))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_album, container, false)
        (activity as AppCompatActivity).setSupportActionBar(binding.albumToolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val musicPlayerEnterTransform: MaterialContainerTransform =
            createMusicPlayerTransform(
                requireContext(),  /* entering= */
                true,
                binding.fab,
                binding.musicPlayerContainer
            )
        binding.fab.setOnClickListener {
            TransitionManager.beginDelayedTransition(container!!, musicPlayerEnterTransform)
            binding.fab.visibility = View.GONE
            binding.musicPlayerContainer.visibility = View.VISIBLE

            binding.nestedScrollView.layoutTransition
                .enableTransitionType(LayoutTransition.CHANGING)

        }

        val musicPlayerExitTransform: MaterialContainerTransform =
            createMusicPlayerTransform(
                requireContext(),  /* entering= */
                false,
                binding.musicPlayerContainer,
                binding.fab
            )

        binding.musicPlayerContainer.setOnClickListener(
            View.OnClickListener { v: View? ->
                TransitionManager.beginDelayedTransition(
                    container!!,
                    musicPlayerExitTransform
                )
                binding.musicPlayerContainer.visibility = View.GONE
                binding.fab.visibility = View.VISIBLE
            })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.transitionName = args.album.id.toString()
        val album = viewModel.albums.find { it.id == args.album.id }
        binding.album = album
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.album_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            activity?.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun createMusicPlayerTransform(
        context: Context, entering: Boolean, startView: View, endView: View
    ): MaterialContainerTransform {
        val musicPlayerTransform = MaterialContainerTransform(context, entering)
        musicPlayerTransform.setPathMotion(MaterialArcMotion())
        musicPlayerTransform.scrimColor = Color.TRANSPARENT
        musicPlayerTransform.startView = startView
        musicPlayerTransform.endView = endView
        musicPlayerTransform.addTarget(endView)
        return musicPlayerTransform
    }
}

