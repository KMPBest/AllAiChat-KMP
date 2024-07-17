package di

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ClipboardManager

expect suspend fun clipData(clipboardManager: ClipboardManager): String?

@Composable
expect fun ImagePicker(
  showFilePicker: Boolean,
  onDismissDialog: () -> Unit,
  onResult: (ByteArray?) -> Unit,
)
