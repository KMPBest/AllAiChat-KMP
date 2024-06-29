package di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

fun initKoin(onKoinStart: KoinApplication.() -> Unit) =
  startKoin {
    onKoinStart()
    modules(
      networkModule,
      viewModelModule,
    )
  }
