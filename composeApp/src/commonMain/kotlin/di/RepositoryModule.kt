package di

import data.repository.GeminiRepositoryImp
import data.repository.GroupRepositoryImp
import domain.repository.GeminiRepository
import domain.repository.GroupRepository
import org.koin.dsl.module

val geminiRepository =
  module {
    single<GeminiRepository> { GeminiRepositoryImp(get(), get()) }
    single<GroupRepository> { GroupRepositoryImp(get()) }
  }
