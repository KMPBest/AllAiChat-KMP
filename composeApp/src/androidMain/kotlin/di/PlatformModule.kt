package di

import android.content.Context
import dev.icerock.moko.permissions.PermissionsController
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module =
  module {
    single { DatabaseDriverFactory() }
    single<PermissionsController> { PermissionsController(get<Context>()) }
  }
