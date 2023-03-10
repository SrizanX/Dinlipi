package com.bitflecks.dinlipi.ui.activity

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bitflecks.dinlipi.R
import com.bitflecks.dinlipi.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var alarmMgr: AlarmManager? = null
    private lateinit var alarmIntent: PendingIntent

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
            val inRoot = (
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

        binding.fabAddReminder.setOnClickListener { navController.navigate(R.id.reminderEditorFragment) }

        viewModel.fabIsShown.observe(this) {
            if (it) {
                showFab()
            } else {
                hideFab()
            }
        }


        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.MINUTE, 38)
            set(Calendar.SECOND, 0)
        }


        alarmMgr = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmIntent = Intent(this, AlarmReceiver::class.java).let { intent ->
            intent.putExtra("asd","message")
            PendingIntent.getBroadcast(this, 0, intent, 0)
        }

        alarmMgr?.setExact(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            alarmIntent
        )
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

class AlarmReceiver: BroadcastReceiver(){
    override fun onReceive(p0: Context?, p1: Intent?) {

        val a = p1?.extras?.getString("asd")
        Log.d("asd", "onReceive: $a")

        p0?.let {
            ContextCompat.startActivity(it, Intent(it, MainActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK), null)
        }
    }

}