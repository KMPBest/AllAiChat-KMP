package screens.chatDetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ChatDetailScreenViewModel() : ViewModel() {
  val searchQuery = mutableStateOf("")

  fun onSearchQueryChange(newQuery: String) {
    searchQuery.value = newQuery
  }
}
