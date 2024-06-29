package utils

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.time.Duration

inline val Duration.inPast: Instant
  get() = Clock.System.now() - this

fun Instant.durationSinceNow(): Duration = Clock.System.now() - this
