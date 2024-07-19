package di

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.ClipboardManager

expect suspend fun clipData(clipboardManager: ClipboardManager): String?

expect fun downloadDirectoryPath(): String?

expect fun setClipData(
  clipboardManager: ClipboardManager,
  message: String,
)

expect fun ByteArray.toComposeImageBitmap(): ImageBitmap
