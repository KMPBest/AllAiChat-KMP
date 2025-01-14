package di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.*
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import utils.BASE_URL
import utils.logging.AppLogger

@OptIn(ExperimentalSerializationApi::class)
val networkModule =
  module {
    single {
      HttpClient {
        install(HttpTimeout) {
          socketTimeoutMillis = 60_000
          requestTimeoutMillis = 60_000
        }
        install(Logging) {
          logger = Logger.DEFAULT
          level = LogLevel.ALL
          logger =
            object : Logger {
              override fun log(message: String) {
                AppLogger.d("NetworkRequest: $message")
              }
            }
        }
        defaultRequest {
          url(BASE_URL)
          contentType(ContentType.Application.Json)
        }
        install(ContentNegotiation) {
          json(
            Json {
              prettyPrint = true
              isLenient = true
              ignoreUnknownKeys = true
              explicitNulls = false
            },
          )
        }
      }
    }
  }
