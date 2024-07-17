package di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.ClipboardManager

actual suspend fun clipData(clipboardManager: ClipboardManager): String? {
  return clipboardManager.getText()?.text.toString().trim()
}

@Composable
actual fun ImagePicker(
  showFilePicker: Boolean,
  onDismissDialog: () -> Unit,
  onResult: (ByteArray?) -> Unit,
) {
  val scope = rememberCoroutineScope()
  val fileType = listOf("jpg", "jpeg", "png")
//  FilePicker
}
