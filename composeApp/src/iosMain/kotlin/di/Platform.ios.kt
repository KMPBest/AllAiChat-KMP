package di

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.text.AnnotatedString
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.get
import kotlinx.cinterop.reinterpret
import org.jetbrains.skia.Image
import platform.Foundation.NSDownloadsDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask
import platform.UIKit.UIApplication
import platform.UIKit.UIImage
import platform.UIKit.UIImageJPEGRepresentation
import platform.UIKit.UIImagePickerController
import platform.UIKit.UIImagePickerControllerCameraCaptureMode
import platform.UIKit.UIImagePickerControllerDelegateProtocol
import platform.UIKit.UIImagePickerControllerEditedImage
import platform.UIKit.UIImagePickerControllerOriginalImage
import platform.UIKit.UIImagePickerControllerSourceType
import platform.UIKit.UINavigationControllerDelegateProtocol
import platform.darwin.NSObject

actual suspend fun clipData(clipboardManager: ClipboardManager): String? =
  clipboardManager
    .getText()
    ?.text
    .toString()
    .trim()

actual fun downloadDirectoryPath(): String? {
  val fileManager = NSFileManager.defaultManager
  val urls = fileManager.URLsForDirectory(NSDownloadsDirectory, NSUserDomainMask)
  val url = urls.firstOrNull() as? NSURL
  return url?.path
}

actual fun ByteArray.toComposeImageBitmap(): ImageBitmap = Image.makeFromEncoded(this).toComposeImageBitmap()

actual fun setClipData(
  clipboardManager: ClipboardManager,
  message: String,
) = clipboardManager.setText(AnnotatedString(message))

actual class SharedImage(
  private val image: UIImage?,
) {
  @OptIn(ExperimentalForeignApi::class)
  actual fun toByteArray(): ByteArray? =
    if (image != null) {
      val imageData =
        UIImageJPEGRepresentation(image, COMPRESSION_QUALITY)
          ?: throw IllegalArgumentException("image data is null")
      val bytes = imageData.bytes ?: throw IllegalArgumentException("image bytes is null")
      val length = imageData.length

      val data: CPointer<ByteVar> = bytes.reinterpret()
      ByteArray(length.toInt()) { index -> data[index] }
    } else {
      null
    }

  actual fun toImageBitmap(): ImageBitmap? {
    val byteArray = toByteArray()
    return if (byteArray != null) {
      Image.makeFromEncoded(byteArray).toComposeImageBitmap()
    } else {
      null
    }
  }

  private companion object {
    const val COMPRESSION_QUALITY = 0.99
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
  val imagePicker = UIImagePickerController()
  val cameraDelegate =
    object :
      NSObject(),
      UIImagePickerControllerDelegateProtocol,
      UINavigationControllerDelegateProtocol {
      override fun imagePickerController(
        picker: UIImagePickerController,
        didFinishPickingMediaWithInfo: Map<Any?, *>,
      ) {
        val image =
          didFinishPickingMediaWithInfo.getValue(UIImagePickerControllerEditedImage) as? UIImage
            ?: didFinishPickingMediaWithInfo.getValue(
              UIImagePickerControllerOriginalImage,
            ) as? UIImage
        onResult.invoke(SharedImage(image))
        picker.dismissViewControllerAnimated(true, null)
      }
    }

  return CameraManager {
    imagePicker.setSourceType(UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera)
    imagePicker.setAllowsEditing(true)
    imagePicker.setCameraCaptureMode(
      UIImagePickerControllerCameraCaptureMode.UIImagePickerControllerCameraCaptureModePhoto,
    )
    imagePicker.setDelegate(cameraDelegate)
    UIApplication.sharedApplication.keyWindow?.rootViewController?.presentViewController(
      imagePicker,
      true,
      null,
    )
  }
}
