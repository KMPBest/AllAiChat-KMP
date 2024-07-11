package di

import org.koin.dsl.module
import screens.chatDetail.ChatDetailScreenViewModel
import screens.home.HomeViewModel
import utils.AppCoroutineDispatchers
import utils.AppCoroutineDispatchersImpl

val viewModelModule =
  module {
    single<AppCoroutineDispatchers> { AppCoroutineDispatchersImpl() }
    single { ChatDetailScreenViewModel(get(), get()) }
    single { HomeViewModel(get(), get()) }
  }
