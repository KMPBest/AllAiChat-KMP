package components.main

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
import androidx.compose.ui.window.DialogProperties
import configs.uis.LightBgColor
import configs.uis.White
import screens.home.HomeViewModel

@Composable
fun Alert(homeViewModel: HomeViewModel) {
  if (homeViewModel.isShowAlertDialog) {
    AlertDialog(
      properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = false),
      icon = {
        Icon(Icons.Default.Settings, contentDescription = "", tint = Color.White)
      },
      confirmButton = {
        Button(
          onClick = {
            homeViewModel.isShowAlertDialog = false
          },
          colors =
            ButtonDefaults.buttonColors(
              containerColor = Color.White,
            ),
        ) {
          Text("Cancel")
        }
      },
      onDismissRequest = {
        homeViewModel.isShowAlertDialog = false
      },
      containerColor = LightBgColor,
      title = {
        Text(homeViewModel.alertTitleText, style = MaterialTheme.typography.titleSmall, color = Color.White)
      },
      text = {
        Text(homeViewModel.alertDescText, style = MaterialTheme.typography.titleSmall, color = Color.White)
      },
    )
  }
}
