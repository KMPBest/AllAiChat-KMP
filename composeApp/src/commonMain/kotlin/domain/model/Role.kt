package domain.model

import kotlinx.serialization.Serializable

@Serializable
enum class Role {
  YOU,
  CHAT,
  ERROR,
}
