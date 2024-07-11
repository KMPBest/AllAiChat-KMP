package utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController

inline fun Modifier.noRippleClickable(
  enabled: Boolean = true,
  crossinline onClick: () -> Unit,
): Modifier =
  composed {
    clickable(
      enabled = enabled,
      indication = null,
      interactionSource = remember { MutableInteractionSource() },
    ) {
      onClick()
    }
  }

fun Modifier.hideKeyboardOnOutsideClick(): Modifier =
  composed {
    val controller = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    this then
      Modifier.noRippleClickable {
        controller?.hide()
        focusManager.clearFocus()
      }
  }
