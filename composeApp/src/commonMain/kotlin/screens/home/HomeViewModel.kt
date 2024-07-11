package screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.useCases.GroupUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import utils.AppCoroutineDispatchers

class HomeViewModel(
  private val groupUseCases: GroupUseCases,
  private val appCoroutineDispatchers: AppCoroutineDispatchers,
) : ViewModel() {
  private val _groupUiState = MutableStateFlow(HomeUiState())
  val groupUiState = _groupUiState.asStateFlow()

  init {
    getGroupList()
  }

  fun getGroupList() {
    viewModelScope.launch(appCoroutineDispatchers.io) {
      _groupUiState.update {
        HomeUiState(group = groupUseCases.getAllGroup())
      }
    }
  }

  fun addNewGroup(
    groupId: String,
    groupName: String,
    date: String,
    icon: String,
  ) {
    viewModelScope.launch(appCoroutineDispatchers.io) {
      groupUseCases.insertGroup(groupId, groupName, date, icon)
      getGroupList()
    }
  }

  fun setApikeyLocalStorage(apiKeyText: String) {
  }
}
