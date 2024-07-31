package screens.chatDetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.model.Role
import domain.useCases.GeminiChatUseCases
import domain.useCases.GroupUseCases
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
  private val groupUseCases: GroupUseCases,
) : ViewModel() {
  private val _chatUiState = MutableStateFlow(ChatDetailUiState())
  val chatUiState = _chatUiState.asStateFlow()

  var message by mutableStateOf("")
  var failedMessageId by mutableStateOf("")
  var groupId by mutableStateOf("")

  var bottomExpanded by mutableStateOf(true)

  fun getGroupDetail(groupId: String) {
    viewModelScope.launch(appCoroutineDispatchers.io) {
      val groupDetail = groupUseCases.getGroupDetail(groupId)
      _chatUiState.update {
        it.copy(
          groupDetail = groupDetail,
        )
      }
    }
  }

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
          isApiLoading = true,
        )
      }
      addToMessage(groupId, messageId, content, Role.YOU, isPending = true, images)
      try {
        val gemini = chatUseCases.getContentWithImage(content, apiKey, images)
        val generatedContent =
          gemini.candidates[0]
            .content.parts[0]
            .text
        val botId = generateRandomKey()
        handleContent(messageId, false)
        failedMessageId = ""
        addToMessage(groupId, botId, generatedContent, Role.CHAT, isPending = false, emptyList())
        _chatUiState.update {
          _chatUiState.value.copy(
            isApiLoading = false,
          )
        }
        AppLogger.d(generatedContent)
      } catch (e: Exception) {
        val errorMessage =
          if (e.message != null) {
            if (e.message.toString().contains("Illegal input: Field")) {
              "Failed to generate content. Please try again."
            } else {
              e.message.toString()
            }
          } else {
            "Failed to generate content. Please try again."
          }
        handleError(messageId, errorMessage)
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

  private fun handleContent(
    messageId: String,
    isPending: Boolean,
  ) {
    viewModelScope.launch(appCoroutineDispatchers.io) {
      chatUseCases.updatePending(messageId, isPending)
      getMessageList()
    }
  }

  private fun handleError(
    messageId: String,
    errorMessage: String,
  ) {
    viewModelScope.launch(appCoroutineDispatchers.io) {
      chatUseCases.updatePending(messageId, false)
      val errorId = generateRandomKey()
      addToMessage(groupId, errorId, errorMessage, Role.ERROR, isPending = false, emptyList())
      failedMessageId = ""
      _chatUiState.update {
        _chatUiState.value.copy(isApiLoading = false)
      }
    }
  }
}
