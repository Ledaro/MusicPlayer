package com.example.musicplayer.ui.albums

import android.os.Bundle
import android.view.View
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.R
import com.example.musicplayer.data.Album
import com.example.musicplayer.databinding.FragmentAlbumsBinding
import com.google.android.material.card.MaterialCardView

class AlbumsFragment : Fragment(R.layout.fragment_albums) /*AlbumsAdapter.OnItemClickListener*/  {
    private var _binding: FragmentAlbumsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AlbumsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentAlbumsBinding.bind(view)

        val albumsAdapter = AlbumsAdapter()

        albumsAdapter.onItemClickListener = object : AlbumsAdapter.OnItemClickListener {
            override fun onItemClick(albumId: Int, cardView: CardView) {
                val extras = FragmentNavigatorExtras(
                    cardView to albumId.toString()
                )
                val action = AlbumsFragmentDirections.actionAlbumsFragmentToAlbumFragment(albumId)
                findNavController().navigate(action, extras)
            }
        }


        binding.apply {
            albumsRecyclerView.apply {
                adapter = albumsAdapter
                layoutManager = GridLayoutManager(requireContext(), 2)
                setHasFixedSize(true)
            }
        }

        albumsAdapter.submitList(viewModel.albums)

    }

    /*    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button2.setOnClickListener {
            findNavController().navigate(R.id.action_libraryFragment_to_test2Fragment)
        }
    }*/

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

/*    override fun onItemClick(albumId:Int, cardView: CardView) {
        val extras = FragmentNavigatorExtras(
            cardView to id.toString()
        )
        val action = AlbumsFragmentDirections.actionAlbumsFragmentToAlbumFragment(id)
        findNavController().navigate(action, extras)
    }*/

}