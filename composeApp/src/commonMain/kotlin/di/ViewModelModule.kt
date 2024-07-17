package di

import org.koin.dsl.module
import screens.chatDetail.ChatDetailScreenViewModel
import screens.home.HomeViewModel
import screens.main.MainViewModel
import utils.AppCoroutineDispatchers
import utils.AppCoroutineDispatchersImpl

val viewModelModule =
  module {
    single<AppCoroutineDispatchers> { AppCoroutineDispatchersImpl() }
    single { ChatDetailScreenViewModel(get(), get()) }
    single { HomeViewModel(get(), get()) }
    single { MainViewModel(get(), get()) }
  }
