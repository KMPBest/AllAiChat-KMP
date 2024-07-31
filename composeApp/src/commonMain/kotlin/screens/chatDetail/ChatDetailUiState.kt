package screens.chatDetail

import domain.model.ChatMessage
import domain.model.Group

data class ChatDetailUiState(
  val message: List<ChatMessage> = emptyList(),
  val isApiLoading: Boolean = false,
  val isLoading: Boolean = false,
  val groupDetail: Group = Group("", "", "", ""),
)
