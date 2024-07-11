package di

import domain.useCases.GeminiChatUseCases
import domain.useCases.GroupUseCases
import org.koin.dsl.module

val useCaseModule =
  module {
    factory { GeminiChatUseCases(get()) }
    factory { GroupUseCases(get()) }
  }
