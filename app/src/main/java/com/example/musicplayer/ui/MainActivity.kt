package com.example.musicplayer.ui

import android.os.Bundle
import android.transition.Fade
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.RequestManager
import com.example.musicplayer.R
import com.example.musicplayer.data.entities.SongNew
import com.example.musicplayer.databinding.ActivityMainBinding
import com.example.musicplayer.other.Status
import com.example.musicplayer.ui.albums.song.SongBottomSheetFragment
import com.example.musicplayer.ui.albums.song.SwipeSongsAdapterNew
import com.example.musicplayer.util.toSong
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    @Inject
    lateinit var glide: RequestManager

    @Inject
    lateinit var swipeSongsAdapterNew: SwipeSongsAdapterNew

    private var currentPlayingSong: SongNew? = null

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<RelativeLayout>

    private lateinit var bottomSheetFragmentContainer: FragmentContainerView
    private lateinit var songBottomFragment: SongBottomSheetFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomSheetFragmentContainer = binding.bottomSheetFragmentContainer
        songBottomFragment =
            supportFragmentManager.findFragmentById(bottomSheetFragmentContainer.id) as SongBottomSheetFragment

        var vpSong = songBottomFragment.view?.findViewById<ViewPager2>(R.id.vpSong)
        vpSong?.adapter = swipeSongsAdapterNew

        setupNavigation()
        setupBottomSheet()
        subscribeToObserver()

        if (savedInstanceState != null) {
            bottomSheetBehavior.state = savedInstanceState.getInt(
                BOTTOM_SHEET_STATE_KEY,
                BottomSheetBehavior.STATE_COLLAPSED
            )
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(BOTTOM_SHEET_STATE_KEY, bottomSheetBehavior.state)
    }

    private fun switchViewPagerToCurrentSong(songNew: SongNew) {
        var vpSong = songBottomFragment.view?.findViewById<ViewPager2>(R.id.vpSong)
        vpSong?.adapter = swipeSongsAdapterNew

        val newItemIndex = swipeSongsAdapterNew.songs.indexOf(songNew)
        if (newItemIndex != -1) {
            vpSong?.currentItem = newItemIndex
            currentPlayingSong = songNew
        }
    }

    private fun subscribeToObserver() {
        mainViewModel.mediaItems.observe(this) { result ->
            when (result.status) {
                Status.SUCCESS -> {
                    result.data?.let { songs ->
                        swipeSongsAdapterNew.songs = songs
                        if (songs.isNotEmpty()) {
                            songBottomFragment.view?.findViewById<ImageView>(R.id.ivCurSongImage)
                                ?.let { ivCurSongImage ->
                                    glide.load((currentPlayingSong ?: songs[0]).imageUrl)
                                        .into(ivCurSongImage)
                                }
                        }
                        switchViewPagerToCurrentSong(currentPlayingSong ?: return@observe)
                    }
                }
                Status.ERROR -> Unit
                Status.LOADING -> Unit
            }
        }

        mainViewModel.currentPlayingSong.observe(this) { song ->
            song?.let {
                currentPlayingSong = it.toSong()
                songBottomFragment.view?.findViewById<ImageView>(R.id.ivCurSongImage)
                    ?.let { ivCurSongImage ->
                        glide.load(currentPlayingSong?.imageUrl).into(ivCurSongImage)
                    }
                switchViewPagerToCurrentSong(currentPlayingSong ?: return@observe)
            }
        }
    }

    private fun setupBottomSheet() {
        val bottomSheet = binding.bottomDrawer
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    songBottomFragment.view?.findViewById<CardView>(R.id.bottoms_sheet_player)?.visibility =
                        View.GONE
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                val cardView =
                    songBottomFragment.view?.findViewById<CardView>(R.id.bottoms_sheet_player)
                val constraintLayout =
                    songBottomFragment.view?.findViewById<ConstraintLayout>(R.id.song_layout)

                if (slideOffset >= 0) {
                    val alpha = 1 - slideOffset // Inverse of slide offset
                    val transition = TransitionSet().apply {
                        addTransition(Fade(Fade.OUT).apply {
                            addTarget(cardView)
                            duration = 150
                        })
                        addTransition(Fade(Fade.IN).apply {
                            addTarget(constraintLayout)
                            duration = 150
                        })
                    }

                    TransitionManager.beginDelayedTransition(bottomSheet as ViewGroup?, transition)

                    if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED && alpha == 0f) {
                        cardView?.visibility = View.GONE
                        constraintLayout?.visibility = View.VISIBLE
                    } else if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED && alpha == 1f) {
                        cardView?.visibility = View.VISIBLE
                        constraintLayout?.visibility = View.GONE
                    } else {
                        cardView?.visibility = View.VISIBLE
                        constraintLayout?.visibility = View.VISIBLE
                    }

                    cardView?.alpha = alpha
                    constraintLayout?.alpha = 1 - alpha
                }
            }
        })
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

/*        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.albumFragmentNew -> hideBottomNav()
                else -> showBottomNav()
            }
        }*/

        binding.bottomNavigationView.setupWithNavController(navController)
    }

    private fun showBottomNav() {
        binding.bottomNavigationView.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        binding.bottomNavigationView.visibility = View.GONE
    }

    companion object {
        private const val BOTTOM_SHEET_STATE_KEY = "bottom_sheet_state"
    }
}
