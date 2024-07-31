package components.global.alert

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.window.DialogProperties
import configs.uis.LightBgColor
import configs.uis.White

@Composable
fun Alert(alertModel: AlertViewModel) {
  val keyboardController = LocalSoftwareKeyboardController.current

  if (alertModel.visible) {
    AlertDialog(
      properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = false),
      icon = {
        Icon(Icons.Default.Settings, contentDescription = "", tint = Color.White)
      },
      confirmButton = {
        Button(
          onClick = {
            alertModel.onConfirm()
          },
          colors =
            ButtonDefaults.buttonColors(
              containerColor = Color.White,
            ),
        ) {
          Text(alertModel.confirmLabel)
        }
      },
      dismissButton = {
        Button(
          onClick = {
            alertModel.onHideAlert()
          },
          colors =
            ButtonDefaults.buttonColors(
              containerColor = Color.White,
            ),
        ) {
          Text(alertModel.cancelLabel)
        }
      },
      onDismissRequest = {
        alertModel.onHideAlert()
        keyboardController?.hide()
      },
      containerColor = LightBgColor,
      title = {
        Text(alertModel.title, style = MaterialTheme.typography.titleSmall, color = Color.White)
      },
      text = {
        Text(alertModel.message, style = MaterialTheme.typography.bodySmall, color = Color.White)
      },
    )
  }
}
