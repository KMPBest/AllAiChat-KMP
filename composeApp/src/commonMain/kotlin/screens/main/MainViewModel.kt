package screens.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.russhwolf.settings.set
import domain.useCases.GroupUseCases
import utils.AppCoroutineDispatchers

class MainViewModel(
  private val appCoroutineDispatchers: AppCoroutineDispatchers,
  private val groupUseCases: GroupUseCases,
) : ViewModel()
