package di

import components.global.alert.AlertViewModel
import org.koin.dsl.module
import screens.chatDetail.ChatDetailScreenViewModel
import screens.home.HomeViewModel
import screens.main.MainViewModel
import utils.AppCoroutineDispatchers
import utils.AppCoroutineDispatchersImpl
import viewModels.FilePickerViewModel
import viewModels.PermissionsViewModel

val viewModelModule =
  module {
    single<AppCoroutineDispatchers> { AppCoroutineDispatchersImpl() }
    single { ChatDetailScreenViewModel(get(), get(), get()) }
    single { HomeViewModel(get(), get()) }
    single { MainViewModel(get(), get()) }
    single { AlertViewModel() }
    factory { FilePickerViewModel() }
    factory { PermissionsViewModel(get(), get()) }
  }
