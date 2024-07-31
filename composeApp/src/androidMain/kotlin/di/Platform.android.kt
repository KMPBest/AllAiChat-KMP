package di

import android.content.ContentResolver
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import com.ngdang.outcome.ComposeFileProvider
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

actual suspend fun clipData(clipboardManager: ClipboardManager): String? =
  clipboardManager
    .getText()
    ?.text
    .toString()
    .trim()

actual fun downloadDirectoryPath(): String? = null

actual fun ByteArray.toComposeImageBitmap(): ImageBitmap = BitmapFactory.decodeByteArray(this, 0, size).asImageBitmap()

actual fun setClipData(
  clipboardManager: ClipboardManager,
  message: String,
) = clipboardManager.setText(AnnotatedString(message))

actual class SharedImage(
  private val bitmap: android.graphics.Bitmap?,
) {
  actual fun toByteArray(): ByteArray? =
    if (bitmap != null) {
      val byteArrayOutputStream = ByteArrayOutputStream()
      @Suppress("MagicNumber")
      bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
      byteArrayOutputStream.toByteArray()
    } else {
      println("toByteArray: null")
      null
    }

  actual fun toImageBitmap(): ImageBitmap? {
    val byteArray = toByteArray()
    return if (byteArray != null) {
      BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size).asImageBitmap()
    } else {
      println("toImageBitmap: null")
      null
    }
  }
}

actual class CameraManager actual constructor(
  private val onLaunch: () -> Unit,
) {
  actual fun launch() {
    onLaunch()
  }
}

@Composable
actual fun rememberCameraManager(onResult: (SharedImage?) -> Unit): CameraManager {
  val context = LocalContext.current
  val contentResolver: ContentResolver = context.contentResolver
  var tempPhotoUri by remember {
    mutableStateOf(value = Uri.EMPTY)
  }
  val cameraLauncher =
    rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicture(), onResult = { success ->
      if (success) {
        onResult.invoke(SharedImage(BitmapUtils.getBitmapFromUri(tempPhotoUri, contentResolver)))
      }
    })
  return remember {
    CameraManager(
      onLaunch = {
        tempPhotoUri = ComposeFileProvider.getImageUri(context)
        cameraLauncher.launch(tempPhotoUri)
      },
    )
  }
}
