package data.database

import app.cash.sqldelight.ColumnAdapter
import com.ngdang.outcome.GeminiApiChatDB
import com.ngdang.outcome.Message
import di.DatabaseDriverFactory
import domain.model.Role
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class SharedDatabase(
  private val driverProvider: DatabaseDriverFactory,
) {
  private var database: GeminiApiChatDB? = null

  @OptIn(ExperimentalEncodingApi::class)
  val byteArrayToStringAdapter =
    object : ColumnAdapter<List<ByteArray>, String> {
      override fun decode(databaseValue: String): List<ByteArray> {
        if (databaseValue.isNotEmpty()) {
          val encodeList = databaseValue.split(",")
          val decodeList = ArrayList<ByteArray>()

          for (encodedBytes in encodeList) {
            val byteArray = Base64.decode(encodedBytes)
            decodeList.add(byteArray)
          }

          return decodeList
        }

        return emptyList()
      }

      override fun encode(value: List<ByteArray>): String {
        if (value.isNotEmpty()) {
          val encodedList = ArrayList<String>()
          for (byteArray in value) {
            val encodeBytes = Base64.encode(byteArray)
            encodedList.add(encodeBytes)
          }
          return encodedList.joinToString(",")
        }
        return ""
      }
    }

  private val roleToStringAdapter =
    object : ColumnAdapter<Role, String> {
      override fun decode(databaseValue: String): Role {
        return Role.valueOf(databaseValue)
      }

      override fun encode(value: Role): String {
        return value.name
      }
    }

  private suspend fun initDatabase() {
    if (database == null) {
      database =
        GeminiApiChatDB.invoke(
          driverProvider.createDriver(),
          MessageAdapter =
            Message.Adapter(
              byteArrayToStringAdapter,
              roleToStringAdapter,
            ),
        )
    }
  }

  suspend operator fun <R> invoke(block: suspend (GeminiApiChatDB) -> R): R {
    initDatabase()
    return block(database!!)
  }
}
