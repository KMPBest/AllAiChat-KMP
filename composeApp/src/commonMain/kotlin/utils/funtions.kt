package utils

import kotlin.random.Random

fun generateRandomKey(): String {
  val charPool = ('a'..'z') + ('A'..'Z') + ('0'..'9')
  val keySize = 32
  val key =
    (1..keySize)
      .map { Random.nextInt(0, charPool.size).let { charPool[it] } }
      .joinToString("")

  return key
}
