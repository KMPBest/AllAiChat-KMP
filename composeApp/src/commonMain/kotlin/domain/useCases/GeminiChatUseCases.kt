package domain.useCases

import domain.model.ChatMessage
import domain.model.Gemini
import domain.model.Role
import domain.repository.GeminiRepository
import org.koin.core.component.KoinComponent

class GeminiChatUseCases(
  private val geminiRepository: GeminiRepository,
) : KoinComponent {
  suspend fun getContentWithImage(
    content: String,
    apiKey: String,
    images: List<ByteArray>,
  ): Gemini =
    if (images.isNotEmpty()) {
      geminiRepository.generateContentWithImage(content, apiKey, images)
    } else {
      geminiRepository.generateContent(content, apiKey)
    }

  suspend fun insertMessage(
    messageId: String,
    groupId: String,
    text: String,
    images: List<ByteArray>,
    participant: Role,
    isPending: Boolean,
  ) {
    geminiRepository.insertMessage(messageId, groupId, text, images, participant, isPending)
  }

  suspend fun deleteAllMessage(groupId: String) {
    geminiRepository.deleteAllMessage(groupId)
  }

  suspend fun getAllMessageByGroupId(groupId: String): List<ChatMessage> =
    geminiRepository.getMessageListByGroupId(groupId)

  suspend fun updatePending(
    messageId: String,
    isPending: Boolean,
  ) {
    geminiRepository.updatePendingStatus(messageId, isPending)
  }
}
