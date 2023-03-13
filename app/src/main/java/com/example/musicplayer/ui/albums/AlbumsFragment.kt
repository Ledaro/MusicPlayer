package com.example.musicplayer.ui.albums

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.FrameLayout
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.example.musicplayer.R
import com.example.musicplayer.data.Album
import com.example.musicplayer.databinding.FragmentAlbumsBinding
import com.google.android.material.transition.Hold
import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.material.transition.MaterialSharedAxis

class AlbumsFragment : Fragment(R.layout.fragment_albums), AlbumsAdapter.OnItemClickListener {
    private var _binding: FragmentAlbumsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AlbumsViewModel by viewModels()
    private var listState: Parcelable? = null

    companion object {
        private val ALBUM_RECYCLER_VIEW_ID = ViewCompat.generateViewId()
        private const val GRID_SPAN_COUNT = 2
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAlbumsBinding.bind(view)

        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

        exitTransition = null

        setList(
            viewModel.isGridView,
            viewModel.isListSorted,
            MaterialSharedAxis(MaterialSharedAxis.Z, true)
        )

        binding.albumsToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_change_view_type -> {
                    onViewToggleClick()
                    true
                }
                R.id.action_sort_by_alpha -> {
                    onSortClick()
                    true

                }
                else -> false
            }
        }
    }

    override fun onItemClick(album: Album, cardView: CardView) {
        val extras = FragmentNavigatorExtras(
            cardView to album.id.toString()
        )
        val action = AlbumsFragmentDirections.actionAlbumsFragmentToAlbumFragment(album)
        findNavController().navigate(action, extras)

        exitTransition = Hold().apply {
            duration = 300
        }
    }

    private fun createRecyclerView(isGridView: Boolean): RecyclerView {
        val recyclerView = RecyclerView(requireContext())
        recyclerView.id = ALBUM_RECYCLER_VIEW_ID
        recyclerView.layoutParams =
            FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
        val verticalPadding =
            resources.getDimensionPixelSize(R.dimen.cat_music_player_album_list_padding_vertical)
        recyclerView.setPadding(0, verticalPadding, 0, verticalPadding)
        recyclerView.clipToPadding = false

        if (isGridView) {
            recyclerView.layoutManager = GridLayoutManager(requireContext(), GRID_SPAN_COUNT)
        } else {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        recyclerView.isTransitionGroup
        recyclerView.setHasFixedSize(true)

        return recyclerView
    }

    private fun setList(isGridView: Boolean, isListSorted: Boolean, transition: Transition) {
        val recyclerView = createRecyclerView(isGridView)
        val listContainer = binding.listContainer
        val currentRecyclerView = listContainer.getChildAt(0)
        val adapter = AlbumsAdapter(this, isGridView)
        var albums = viewModel.albums

        if (listState != null) {
            recyclerView.layoutManager?.onRestoreInstanceState(listState)
            listState = null
        }
        transition.addTarget(recyclerView)

        if (currentRecyclerView != null) {
            transition.addTarget(currentRecyclerView)
        }
        TransitionManager.beginDelayedTransition(listContainer, transition)

        recyclerView.adapter = adapter

        if (isListSorted) {
            albums = viewModel.albumsSorted.toMutableList()
        }
        adapter.submitList(albums)

        listContainer.removeAllViews()
        listContainer.addView(recyclerView)
    }

    private fun onViewToggleClick() {
        viewModel.toggleGridView()
        setList(viewModel.isGridView, viewModel.isListSorted, MaterialFadeThrough())
    }

    private fun onSortClick() {
        viewModel.toggleListSorting()
        setList(viewModel.isGridView, viewModel.isListSorted, MaterialFadeThrough())
    }

    override fun onDestroyView() {
        val rv =
            requireView().findViewById<RecyclerView>(ALBUM_RECYCLER_VIEW_ID)
        if (rv != null) {
            listState = rv.layoutManager!!.onSaveInstanceState()
        }
        _binding = null
        super.onDestroyView()
    }
}
