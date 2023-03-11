package com.example.musicplayer.ui.albums

import android.os.Bundle
import android.view.View
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.R
import com.example.musicplayer.data.Album
import com.example.musicplayer.databinding.FragmentAlbumsBinding
import com.google.android.material.transition.MaterialFade
import com.google.android.material.transition.MaterialFadeThrough

class AlbumsFragment : Fragment(R.layout.fragment_albums), AlbumsAdapter.OnItemClickListener {
    private var _binding: FragmentAlbumsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AlbumsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAlbumsBinding.bind(view)

        postponeEnterTransition()
        binding.albumsRecyclerView.post { startPostponedEnterTransition() }

        setUpRecyclerview(viewModel.isGridView)

        binding.albumsToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_change_view_type -> {
                    viewModel.toggleGridView()
                    setUpRecyclerview(viewModel.isGridView)
                    true
                }
                else -> false
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialFadeThrough()
        exitTransition = MaterialFade().apply {
            duration = 150
        }
    }

    override fun onItemClick(album: Album, cardView: CardView) {
        val extras = FragmentNavigatorExtras(
            cardView to album.id.toString()
        )
        val action = AlbumsFragmentDirections.actionAlbumsFragmentToAlbumFragment(album)
        findNavController().navigate(action, extras)
    }

    private fun setUpRecyclerview(isGridView: Boolean) {
        val albumsAdapter = AlbumsAdapter(this, isGridView)

        if (isGridView) {
            binding.apply {
                albumsRecyclerView.apply {
                    adapter = albumsAdapter
                    layoutManager = GridLayoutManager(requireContext(), 2)
                    removeItemDecoration(
                        DividerItemDecoration(
                            context,
                            DividerItemDecoration.VERTICAL
                        )
                    )
                    setHasFixedSize(true)
                }
            }

        } else {
            binding.apply {
                albumsRecyclerView.apply {
                    adapter = albumsAdapter
                    layoutManager = LinearLayoutManager(requireContext())
                    addItemDecoration(
                        DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
                    )
                    setHasFixedSize(true)
                }
            }
        }
        albumsAdapter.submitList(viewModel.albums)
        albumsAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
