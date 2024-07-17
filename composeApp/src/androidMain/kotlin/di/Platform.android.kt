package di

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalContext

actual suspend fun clipData(clipboardManager: ClipboardManager): String? {
  return clipboardManager.getText()?.text.toString().trim()
}

@Composable
actual fun ImagePicker(
  showFilePicker: Boolean,
  onDismissDialog: () -> Unit,
  onResult: (ByteArray?) -> Unit,
) {
  val context = LocalContext.current

  val pickMedia =
    rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { imageUri ->
      if (imageUri != null) {
        onResult(context.contentResolver.openInputStream(imageUri)?.readBytes())
      } else {
        onDismissDialog()
      }
    }
  if (showFilePicker) {
    pickMedia.launch(
      PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly),
    )
  }
}
