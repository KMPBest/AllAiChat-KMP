package domain.repository

import domain.model.ChatMessage
import domain.model.Gemini
import domain.model.Role

interface GeminiRepository {
  suspend fun generateContent(
    content: String,
    apiKey: String,
  ): Gemini

  suspend fun generateContentWithImage(
    content: String,
    apiKey: String,
    images: List<ByteArray> = emptyList(),
  ): Gemini

  suspend fun insertMessage(
    messageId: String,
    groupId: String,
    text: String,
    images: List<ByteArray>,
    participant: Role,
    isPending: Boolean,
  )

  suspend fun getMessageListByGroupId(groupId: String): List<ChatMessage>
}
