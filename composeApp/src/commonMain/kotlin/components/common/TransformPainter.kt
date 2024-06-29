package components.common

import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter

fun transformPainter(
  painter: Painter,
  colorFilter: () -> ColorFilter?,
): Painter = TransformPainter(painter, colorFilter)

private class TransformPainter(
  private val painter: Painter,
  private var colorFilter: () -> ColorFilter?,
) : Painter() {
  private var alpha = 1f
  private var fallbackColorFilter: ColorFilter? = null

  override val intrinsicSize get() = painter.intrinsicSize

  override fun applyAlpha(alpha: Float): Boolean {
    if (this.alpha != alpha) {
      this.alpha = alpha
      return true
    }
    return false
  }

  override fun applyColorFilter(colorFilter: ColorFilter?): Boolean {
    if (fallbackColorFilter != colorFilter) {
      fallbackColorFilter = colorFilter
      return true
    }
    return false
  }

  override fun DrawScope.onDraw() {
    with(painter) {
      draw(size = size, alpha = alpha, colorFilter = colorFilter() ?: fallbackColorFilter)
    }
  }
}
