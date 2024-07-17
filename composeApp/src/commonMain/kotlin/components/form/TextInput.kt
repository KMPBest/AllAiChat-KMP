package components.form

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.isUnspecified
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import configs.uis.BlackLight
import configs.uis.Gray
import configs.uis.White

@Composable
fun TextInput(
  modifier: Modifier = Modifier,
  value: String,
  enabled: Boolean = true,
  onValueChange: (String) -> Unit,
  title: String = "",
  required: Boolean = false,
  error: String = "",
  bgColor: Color = Color.Unspecified,
  placeholder: String = "",
  maxLines: Int = 1,
  leadingIcon:
    @Composable()
    (() -> Unit)? = null,
  trailingIcon:
    @Composable()
    (() -> Unit)? = null,
) {
  val cursorColor = Color.Black
  val backgroundColor =
    if (bgColor.isUnspecified) {
      if (enabled) BlackLight else Color.LightGray.copy(alpha = 0.4f)
    } else {
      bgColor
    }
  val shape = RoundedCornerShape(8.dp)

  WrapInput(
    title = title,
    required = required,
    error = error,
    modifier = modifier,
  ) {
    TextField(
      modifier =
        Modifier
          .fillMaxWidth().clip(shape),
      enabled = enabled,
      value = value,
      onValueChange = { onValueChange(it) },
      keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
      maxLines = maxLines,
      placeholder = {
        Text(
          text = placeholder,
          color = Gray,
        )
      },
      colors =
        TextFieldDefaults.colors(
          unfocusedContainerColor = backgroundColor,
          focusedContainerColor = backgroundColor,
          focusedIndicatorColor = Color.Transparent,
          unfocusedIndicatorColor = Color.Transparent,
          focusedTextColor = Color.White,
          unfocusedTextColor = Color.White,
          focusedPlaceholderColor = Color.White,
          unfocusedPlaceholderColor = Color.White,
          unfocusedLabelColor = Color.White,
          focusedLabelColor = Color.White,
          cursorColor = Color.White,
          selectionColors = TextSelectionColors(White, White),
        ),
      leadingIcon = leadingIcon,
      trailingIcon = trailingIcon,
    )
  }
}
