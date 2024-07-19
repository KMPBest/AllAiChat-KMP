package screens.chatDetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.model.Role
import domain.useCases.GeminiChatUseCases
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import utils.AppCoroutineDispatchers
import utils.generateRandomKey
import utils.logging.AppLogger

class ChatDetailScreenViewModel(
  private val appCoroutineDispatchers: AppCoroutineDispatchers,
  private val chatUseCases: GeminiChatUseCases,
) : ViewModel() {
  val searchQuery = mutableStateOf("")
  private val _chatUiState = MutableStateFlow(ChatDetailUiState())
  val chatUiState = _chatUiState.asStateFlow()

  var message by mutableStateOf("")
  var failedMessageId by mutableStateOf("")
  var groupId by mutableStateOf("")

  fun getMessageList(
    isClicked: Boolean = false,
    _groupId: String = groupId,
  ) {
    viewModelScope.launch(appCoroutineDispatchers.io) {
      if (isClicked) {
        _chatUiState.update {
          _chatUiState.value.copy(
            isLoading = true,
          )
        }
      }

      delay(500)
      _chatUiState.update {
        _chatUiState.value.copy(
          message = chatUseCases.getAllMessageByGroupId(_groupId).reversed(),
          isLoading = false,
        )
      }
    }
  }

  fun generateContentWithText(
    groupId: String,
    content: String,
    apiKey: String,
    imageUris: List<ByteArray>,
  ) {
    val images = imageUris.toList()
    viewModelScope.launch(appCoroutineDispatchers.io) {
      val messageId = generateRandomKey()
      failedMessageId = messageId
      message = ""
      _chatUiState.update {
        _chatUiState.value.copy(
          isLoading = true,
        )
      }
      addToMessage(groupId, messageId, content, Role.YOU, isPending = true, images)
      try {
        val gemini = chatUseCases.getContentWithImage(content, apiKey, images)
        val generatedContent = gemini.candidates[0].content.parts[0].text
        val botId = generateRandomKey()

        failedMessageId = ""
        addToMessage(groupId, botId, generatedContent, Role.CHAT, isPending = false, emptyList())
        _chatUiState.update {
          _chatUiState.value.copy(
            isLoading = false,
          )
        }
        AppLogger.d(generatedContent)
      } catch (e: Exception) {
        AppLogger.e("[ERROR ] ${e.message}")
      }
    }
  }

  private fun addToMessage(
    groupId: String,
    messageId: String,
    text: String,
    sender: Role,
    isPending: Boolean,
    images: List<ByteArray>,
  ) {
    viewModelScope.launch(appCoroutineDispatchers.io) {
      chatUseCases.insertMessage(
        messageId,
        groupId,
        text,
        images,
        sender,
        isPending,
      )
      getMessageList()
    }
  }

  fun onSearchQueryChange(newQuery: String) {
    searchQuery.value = newQuery
  }
}
