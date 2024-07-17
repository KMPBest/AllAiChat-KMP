package com.ngdang.outcome

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import org.koin.mp.KoinPlatform
import screens.main.MainViewModel

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      val mainViewModel: MainViewModel = KoinPlatform.getKoin().get()
      App(mainViewModel)
    }
  }
}
