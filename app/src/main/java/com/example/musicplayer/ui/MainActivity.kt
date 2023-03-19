package com.example.musicplayer.ui

import android.os.Bundle
import android.transition.Fade
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.RequestManager
import com.example.musicplayer.R
import com.example.musicplayer.databinding.ActivityMainBinding
import com.example.musicplayer.ui.albums.album.SongBottomSheetFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var glide: RequestManager
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<RelativeLayout>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()
        setupBottomSheet()

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

    private fun setupBottomSheet() {
        val bottomSheetFragmentContainer =
            binding.bottomSheetFragmentContainer
        val songBottomFragment =
            supportFragmentManager.findFragmentById(bottomSheetFragmentContainer.id) as SongBottomSheetFragment


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

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.albumFragmentNew -> hideBottomNav()
                else -> showBottomNav()
            }
        }

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
