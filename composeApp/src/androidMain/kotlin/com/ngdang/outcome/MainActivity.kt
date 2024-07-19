package com.ngdang.outcome

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import io.github.vinceglb.filekit.core.FileKit
import org.koin.mp.KoinPlatform
import screens.main.MainViewModel

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    FileKit.init(this)

    setContent {
      val mainViewModel: MainViewModel = KoinPlatform.getKoin().get()
      App(mainViewModel)
    }
  }
}
