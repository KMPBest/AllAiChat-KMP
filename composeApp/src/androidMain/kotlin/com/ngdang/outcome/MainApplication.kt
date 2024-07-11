package com.ngdang.outcome

import android.app.Application
import org.koin.android.ext.koin.androidContext
import root.AppInitializer

class MainApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    AppInitializer.initialize {
      androidContext(this@MainApplication)
    }
  }
}
