package screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ngdang.outcome.BuildKonfig
import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set
import domain.useCases.GroupUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import utils.AppCoroutineDispatchers
import utils.GEMINI_API_KEY
import utils.isValidApiKey

class HomeViewModel(
  private val groupUseCases: GroupUseCases,
  private val appCoroutineDispatchers: AppCoroutineDispatchers,
) : ViewModel() {
  private val _groupUiState = MutableStateFlow(HomeUiState())
  val groupUiState = _groupUiState.asStateFlow()
  private val settings = Settings()

  var isShowNewGroupDialog by mutableStateOf(false)
  var groupNameText by mutableStateOf("")

  var apiKeyText by mutableStateOf("")
  var isShowApiKeyDialog by mutableStateOf(false)

  var isShowAlertDialog by mutableStateOf(false)
  var alertTitleText by mutableStateOf("")
  var alertDescText by mutableStateOf("")

  init {
    val apiKey = getApiKeyLocalStorage().trim()
    if (apiKey.isNotEmpty()) {
      if (apiKey.isValidApiKey()) {
        apiKeyText = apiKey
      } else {
        isShowApiKeyDialog = true
      }
      isShowApiKeyDialog = false
    } else {
      isShowApiKeyDialog = true
    }
  }

  fun setApiKeyLocalStorage(apiKey: String) {
    settings[GEMINI_API_KEY] = apiKey
  }

  fun getApiKeyLocalStorage(): String {
    return settings.getString(GEMINI_API_KEY, BuildKonfig.GEMINI_API_KEY)
  }

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

  fun setApikeyLocalStorage(apiKeyText: String) {
    settings[GEMINI_API_KEY] = apiKeyText
  }

  fun getApikeyLocalStorage(): String {
    return settings.getString(GEMINI_API_KEY, BuildKonfig.GEMINI_API_KEY)
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

  fun onNewGroup() {
    val apiKey = getApiKeyLocalStorage().trim()
    if (apiKey.isNotEmpty()) {
      if (apiKey.isValidApiKey()) {
        isShowNewGroupDialog = true
      } else {
        apiKeyText = apiKey
        isShowApiKeyDialog = true
      }
    } else {
      apiKeyText = apiKey
      isShowApiKeyDialog = true
    }
  }
}
