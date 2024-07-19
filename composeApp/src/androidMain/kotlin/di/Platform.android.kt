package di

import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.text.AnnotatedString

actual suspend fun clipData(clipboardManager: ClipboardManager): String? {
  return clipboardManager.getText()?.text.toString().trim()
}

actual fun downloadDirectoryPath(): String? = null

actual fun ByteArray.toComposeImageBitmap(): ImageBitmap {
  return BitmapFactory.decodeByteArray(this, 0, size).asImageBitmap()
}

actual fun setClipData(
  clipboardManager: ClipboardManager,
  message: String,
) {
  return clipboardManager.setText(AnnotatedString(message))
}
