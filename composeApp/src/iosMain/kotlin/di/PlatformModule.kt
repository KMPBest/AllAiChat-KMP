package di

import dev.icerock.moko.permissions.ios.PermissionsController
import dev.icerock.moko.permissions.ios.PermissionsControllerProtocol
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module =
  module {
    single { DatabaseDriverFactory() }
    single<PermissionsControllerProtocol> { PermissionsController() }
  }
