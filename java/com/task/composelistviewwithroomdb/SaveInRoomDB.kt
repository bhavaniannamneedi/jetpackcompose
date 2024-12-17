package com.task.composelistviewwithroomdb

import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.task.composelistviewwithroomdb.presentation.HomeScreen
import com.task.composelistviewwithroomdb.ui.theme.ComposeListviewwithRoomDBTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SaveInRoomDB : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeListviewwithRoomDBTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    HomeScreen()
                }

                val handler: Handler = Handler()

                handler.postDelayed(Runnable { finish() }, 10000)
            }
        }
    }
}