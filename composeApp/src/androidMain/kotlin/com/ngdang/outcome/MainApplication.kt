package com.ngdang.outcome

import android.app.Application
import di.initKoin
import org.koin.android.ext.koin.androidContext

class MainApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    initKoin {
      androidContext(this@MainApplication)
    }
  }
}
