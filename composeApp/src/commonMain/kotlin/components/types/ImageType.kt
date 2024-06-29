package components.types

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.DrawableResource

sealed class ImageType {
  data class Vector(val image: ImageVector) : ImageType()

  data class Bitmap(val image: ImageBitmap) : ImageType()

  data class Resource(val image: DrawableResource) : ImageType()

  data class Url(val image: String) : ImageType()
}
