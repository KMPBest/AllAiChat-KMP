package data.repository

import app.cash.sqldelight.async.coroutines.awaitAsList
import com.ngdang.outcome.Message
import data.database.SharedDatabase
import data.mapper.toGemini
import data.network.gemini.GeminiService
import domain.model.ChatMessage
import domain.model.Gemini
import domain.model.Role
import domain.repository.GeminiRepository
import getPlatform

class GeminiRepositoryImp(
  private val geminiService: GeminiService,
  private val sharedDatabase: SharedDatabase,
) : GeminiRepository {
  private val platform = getPlatform()

  override suspend fun generateContent(
    content: String,
    apiKey: String,
  ): Gemini = geminiService.generateContent(content, apiKey).toGemini()

  override suspend fun generateContentWithImage(
    content: String,
    apiKey: String,
    images: List<ByteArray>,
  ): Gemini = geminiService.generateContentWithImage(content, apiKey, images).toGemini()

  override suspend fun insertMessage(
    messageId: String,
    groupId: String,
    text: String,
    images: List<ByteArray>,
    participant: Role,
    isPending: Boolean,
  ) {
    sharedDatabase { appDatabase ->
      appDatabase.appDatabaseQueries.insertMessage(
        Message(messageId, groupId, text, images, participant, isPending),
      )
    }
  }

  override suspend fun getMessageListByGroupId(groupId: String): List<ChatMessage> {
    val chatList = arrayListOf<ChatMessage>()
    sharedDatabase { appDatabase ->
      chatList.addAll(
        appDatabase.appDatabaseQueries.getChatByGroupId(groupId).awaitAsList().map {
          ChatMessage(
            it.messageId,
            it.chatId,
            it.content,
            it.images,
            it.participant,
            it.isPending,
          )
        },
      )
    }
    return chatList
  }

  override suspend fun deleteAllMessage(groupId: String) {
    sharedDatabase { appDatabase ->
      appDatabase.appDatabaseQueries.deleteAllMessage(groupId)
    }
  }

  override suspend fun updatePendingStatus(
    messageId: String,
    isPending: Boolean,
  ) {
    sharedDatabase { appDatabase ->
      appDatabase.appDatabaseQueries.updateMessageByMessageId(isPending, messageId)
    }
  }
}
