package di

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.text.AnnotatedString
import org.jetbrains.skia.Image
import platform.Foundation.NSDownloadsDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

actual suspend fun clipData(clipboardManager: ClipboardManager): String? {
  return clipboardManager.getText()?.text.toString().trim()
}

actual fun downloadDirectoryPath(): String? {
  val fileManager = NSFileManager.defaultManager
  val urls = fileManager.URLsForDirectory(NSDownloadsDirectory, NSUserDomainMask)
  val url = urls.firstOrNull() as? NSURL
  return url?.path
}

actual fun ByteArray.toComposeImageBitmap(): ImageBitmap {
  return Image.makeFromEncoded(this).toComposeImageBitmap()
}

actual fun setClipData(
  clipboardManager: ClipboardManager,
  message: String,
) {
  return clipboardManager.setText(AnnotatedString(message))
}
