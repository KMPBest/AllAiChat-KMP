package di

import data.network.gemini.GeminiService
import data.network.gemini.GeminiServiceImp
import org.koin.dsl.module

val geminiServiceModule =
  module {
    single<GeminiService> { GeminiServiceImp(get()) }
  }
