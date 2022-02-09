package com.srizan.dinlipi.ui.activity

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.srizan.dinlipi.R
import com.srizan.dinlipi.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainActivityViewModel by viewModels()

    var fabIsOpen = false
    lateinit var animFabOpen: Animation
    lateinit var animFabClose:Animation
    lateinit var animFabRotateClockWise:Animation
    lateinit var animFabRotateAntiClockWise:Animation

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.notesFragment,
                R.id.reminderFragment,
                R.id.voiceNotesFragment
            )
        )
        navController = findNavController(R.id.dinlipi_nav_host)
        setupActionBarWithNavController(navController,appBarConfiguration)
        binding.bottomNav.setupWithNavController(navController)
        
        val listener = NavController.OnDestinationChangedListener{ _, destination, _ ->
            var inRoot = (
                    destination.id == R.id.notesFragment
                            || destination.id == R.id.reminderFragment
                            || destination.id == R.id.voiceNotesFragment)
            viewModel.fabIsShown.value = inRoot

            if (inRoot){
                binding.bottomNav.visibility = View.VISIBLE
            }
            else {
                binding.bottomNav.visibility = View.GONE
            }
        }

        navController.addOnDestinationChangedListener(listener)

        //Initialize Animations
        animFabOpen = AnimationUtils.loadAnimation(applicationContext, R.anim.fab_open)
        animFabClose = AnimationUtils.loadAnimation(applicationContext, R.anim.fab_close)
        animFabRotateClockWise = AnimationUtils.loadAnimation(applicationContext, R.anim.rotate_clockwise)
        animFabRotateAntiClockWise = AnimationUtils.loadAnimation(applicationContext, R.anim.rotate_anticlockwise)


        binding.fab.setOnClickListener {
            toggleFab()
        }
        binding.fabAddNote.setOnClickListener { navController.navigate(R.id.addEditNoteFragment) }
        binding.fabAddVoiceNote.setOnClickListener { closeFab(); navController.navigate(R.id.recorderFragment) }

        viewModel.fabIsShown.observe(this) {
            if (it) {
                showFab()
            } else {
                hideFab()
            }
        }
    }

    private fun showFab() {
        binding.apply {
            fabsGroup.visibility = View.VISIBLE
        }
    }

    private fun hideFab() {
        binding.apply {
            fabsGroup.visibility = View.GONE
        }
    }

    private fun toggleFab(){
        if (fabIsOpen) {
            closeFab()
        } else {
          openFab()
        }
    }
    private fun closeFab(){
        binding.fab.startAnimation(animFabRotateAntiClockWise)
        binding.fabAddNote.startAnimation(animFabClose)
        binding.fabAddReminder.startAnimation(animFabClose)
        binding.fabAddVoiceNote.startAnimation(animFabClose)
        binding.fabAddNote.isClickable = false
        binding.fabAddReminder.isClickable = false
        binding.fabAddVoiceNote.isClickable = false
        fabIsOpen = false
    }

    private fun openFab(){
        binding.fab.startAnimation(animFabRotateClockWise)
        binding.fabAddNote.startAnimation(animFabOpen)
        binding.fabAddReminder.startAnimation(animFabOpen)
        binding.fabAddVoiceNote.startAnimation(animFabOpen)
        binding.fabAddNote.isClickable = true
        binding.fabAddReminder.isClickable = true
        binding.fabAddVoiceNote.isClickable = true
        fabIsOpen = true
    }
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp()
    }
}