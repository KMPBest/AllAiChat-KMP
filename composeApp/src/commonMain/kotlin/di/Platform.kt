package di

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.ClipboardManager

expect suspend fun clipData(clipboardManager: ClipboardManager): String?

expect fun downloadDirectoryPath(): String?

expect fun setClipData(
  clipboardManager: ClipboardManager,
  message: String,
)

expect fun ByteArray.toComposeImageBitmap(): ImageBitmap

expect class SharedImage {
  fun toByteArray(): ByteArray?

  fun toImageBitmap(): ImageBitmap?
}

expect class CameraManager(
  onLaunch: () -> Unit,
) {
  fun launch()
}

@Composable
expect fun rememberCameraManager(onResult: (SharedImage?) -> Unit): CameraManager
