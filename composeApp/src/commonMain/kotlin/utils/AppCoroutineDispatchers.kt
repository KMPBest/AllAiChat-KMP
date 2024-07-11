package utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface AppCoroutineDispatchers {
  val io: CoroutineDispatcher
  val default: CoroutineDispatcher
  val main: CoroutineDispatcher
}

class AppCoroutineDispatchersImpl() : AppCoroutineDispatchers {
  override val io: CoroutineDispatcher
    get() = Dispatchers.Default
  override val default: CoroutineDispatcher
    get() = Dispatchers.Default
  override val main: CoroutineDispatcher
    get() = Dispatchers.Main
}
