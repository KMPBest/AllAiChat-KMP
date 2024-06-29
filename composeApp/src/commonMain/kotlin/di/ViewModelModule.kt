package di

import org.koin.dsl.module
import screens.chatDetail.ChatDetailScreenViewModel

val viewModelModule =
  module {
    single { ChatDetailScreenViewModel() }
  }
