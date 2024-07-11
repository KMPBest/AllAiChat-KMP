package root

import di.databaseModule
import di.geminiRepository
import di.geminiServiceModule
import di.networkModule
import di.platformModule
import di.useCaseModule
import di.viewModelModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import utils.logging.AppLogger

object AppInitializer {
  fun initialize(onKoinStart: KoinApplication.() -> Unit) {
    startKoin {
      onKoinStart()
      modules(
        networkModule,
        viewModelModule,
        useCaseModule,
        geminiRepository,
        geminiServiceModule,
        databaseModule,
        platformModule(),
      )
    }

    AppLogger.initialize()
  }
}
