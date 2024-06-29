package components.form

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun TextInput(
  modifier: Modifier = Modifier,
  value: String,
  enabled: Boolean = true,
  onValueChange: (String) -> Unit,
  title: String = "",
  required: Boolean = false,
  error: String = "",
) {
  val textColor = Color.Black
  val cursorColor = Color.Black
  val textStyle = MaterialTheme.typography.bodySmall
  val mergedTextStyle = textStyle.merge(TextStyle(color = textColor))
  val backgroundColor = if (enabled) Color.White else Color.LightGray.copy(alpha = 0.4f)
  val shape = RoundedCornerShape(8.dp)
  val customTextSelectionColors =
    TextSelectionColors(
      handleColor = cursorColor,
      backgroundColor = MaterialTheme.colorScheme.primary,
    )

  WrapInput(
    title = title,
    required = required,
    error = error,
    modifier = modifier,
  ) {
    CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
      BasicTextField(
        modifier =
          Modifier
            .border(
              width = 1.dp,
              color = Color(0xFFE3E2E2),
              shape = shape,
            )
            .background(
              color = backgroundColor,
              shape = shape,
            ).padding(16.dp)
            .fillMaxWidth(),
        enabled = enabled,
        value = value,
        onValueChange = { onValueChange(it) },
        textStyle = mergedTextStyle,
        cursorBrush = SolidColor(cursorColor),
      )
    }
  }
}
