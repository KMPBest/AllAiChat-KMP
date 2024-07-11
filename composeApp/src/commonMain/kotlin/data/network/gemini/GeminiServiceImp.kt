package data.network.gemini

import data.models.ContentItem
import data.models.GeminiResponseDto
import data.models.RequestBody
import data.models.RequestPart
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import utils.GEMINI_FLASH
import utils.logging.AppLogger

class GeminiServiceImp(
  private val client: HttpClient,
) : GeminiService {
  override suspend fun generateContent(
    content: String,
    apiKey: String,
  ): GeminiResponseDto {
    val parts = mutableListOf<RequestPart>()
    parts.add(RequestPart(text = content))
    val requestBody = RequestBody(contents = listOf(ContentItem(parts = parts)))

    return try {
      val responseText =
        client.post {
          url("v1/models/${GEMINI_FLASH}:generateContent")
          parameter("key", apiKey)
          setBody(Json.encodeToString(requestBody))
        }.body<GeminiResponseDto>()
      AppLogger.e("[Response]: $responseText")
      responseText
    } catch (e: Exception) {
      AppLogger.e("[Error]: create chat ${e.message}")
      throw e
    }
  }

  override suspend fun generateContentWithImage(
    content: String,
    apiKey: String,
    images: List<ByteArray>,
  ): GeminiResponseDto {
    TODO("Not yet implemented")
  }
}
