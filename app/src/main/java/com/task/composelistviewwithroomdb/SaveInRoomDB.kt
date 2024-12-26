package com.task.composelistviewwithroomdb

import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.task.composelistviewwithroomdb.presentation.HomeScreen
import com.task.composelistviewwithroomdb.presentation.HomeViewModel
import com.task.composelistviewwithroomdb.ui.theme.ComposeListviewwithRoomDBTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class SaveInRoomDB : ComponentActivity() {
     var viewModel: HomeViewModel? =null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeListviewwithRoomDBTheme {
                // A surface container using the 'background' color from the theme
                viewModel = hiltViewModel<HomeViewModel>()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val systemUiController = rememberSystemUiController()
                    WindowCompat.setDecorFitsSystemWindows(window, false)

                    SideEffect {
                        systemUiController.setStatusBarColor(
                            color = Color(0xff655D8A),
                        )
                    }

                    HomeScreen()
                    viewModel!!.shouldExitApp.observe(this, Observer { shouldExit ->
                        if (shouldExit) {
                            exitApp()  // Call exit app logic here
                        }
                    })

                    viewModel!!.resetInactivityTimer();
                }
            }
        }
    }

    private fun exitApp() {
        finish()
    }
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        super.onTouchEvent(event)

        // Reset inactivity timer whenever there is a touch event
        println("bhavani onTouchEvent")
        viewModel?.resetInactivityTimer()
        return true
    }

    override fun onResume() {
        super.onResume()
        // Reset inactivity timer when the activity comes to the foreground
        println("bhavani onResume")

        viewModel?.resetInactivityTimer()

    }

    override fun onPause() {
        super.onPause()
        println("bhavani onPause")

        // Cancel the inactivity timeout when the app goes to the background
        viewModel?.onCleared()
    }

    override fun onDestroy() {
        super.onDestroy()
        println("bhavani onDestroy")

        // Cancel the job when the activity is destroyed
        viewModel?.onCleared()
    }




}