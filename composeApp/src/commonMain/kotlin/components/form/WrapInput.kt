package components.form

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import configs.uis.BgOverlay
import configs.uis.Gray

@Composable
fun WrapInput(
  modifier: Modifier = Modifier,
  title: String = "",
  required: Boolean = false,
  error: String = "",
  content: @Composable () -> Unit,
) {
  val shape = RoundedCornerShape(6.dp)

  Column(modifier = modifier.fillMaxWidth()) {
    if (title.isNotEmpty()) {
      Row(
        modifier = Modifier.padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Text(
          text = title,
          style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
          color = Color.White,
        )
        Spacer(modifier = Modifier.width(8.dp))
        if (required) {
          Box(
            modifier =
              Modifier
                .background(color = BgOverlay, shape = shape)
                .border(color = Gray, width = 1.dp, shape = shape)
                .padding(horizontal = 8.dp, vertical = 2.dp),
          ) {
            Text(
              text = "Requried",
              style = MaterialTheme.typography.labelMedium,
              color = Color.White,
            )
          }
        }
      }
    }
    content()
    if (error.isNotEmpty()) {
      Text(
        text = error,
        style = MaterialTheme.typography.labelMedium,
        color = Color.Red,
      )
    }
  }
}
