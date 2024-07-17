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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.window.DialogProperties
import components.form.TextInput
import configs.uis.BlackLight
import configs.uis.LightBgColor
import configs.uis.White
import screens.home.HomeViewModel
import utils.currentDateTimeToString
import utils.generateRandomKey
import utils.isValidApiKey

@Composable
fun NewChatDialog(homeViewModel: HomeViewModel) {
  val coroutine = rememberCoroutineScope()
  val clipboardManager = LocalClipboardManager.current
  val keyboardController = LocalSoftwareKeyboardController.current

  if (homeViewModel.isShowNewGroupDialog) {
    AlertDialog(
      properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = false),
      icon = {
        Icon(Icons.Default.Settings, contentDescription = "", tint = Color.White)
      },
      confirmButton = {
        Button(
          onClick = {
            if (homeViewModel.apiKeyText.isNotEmpty()) {
              if (homeViewModel.apiKeyText.isValidApiKey()) {
                homeViewModel.addNewGroup(
                  generateRandomKey(),
                  homeViewModel.groupNameText.trim(),
                  currentDateTimeToString(),
                  "robot_${(1..8).random()}.png",
                )
                homeViewModel.groupNameText = ""
                homeViewModel.isShowNewGroupDialog = false
                keyboardController?.hide()
              } else {
//                homeViewModel.isShowApiKeyDialog = false
//                homeViewModel.apiKeyText = ""
                homeViewModel.isShowAlertDialog = true
                homeViewModel.alertTitleText = "Invalid API Key"
                homeViewModel.alertDescText = "The API Key you entered is not valid. Please enter a valid API Key."
              }
            } else {
              homeViewModel.isShowAlertDialog = true
              homeViewModel.alertTitleText = "Missing API Key"
              homeViewModel.alertDescText = "An API Key is required to proceed. Please enter your API Key."
            }
          },
          colors =
            ButtonDefaults.buttonColors(
              containerColor = Color.White,
            ),
        ) {
          Text("Save")
        }
      },
      dismissButton = {
        Button(
          onClick = {
            homeViewModel.isShowNewGroupDialog = false
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
        homeViewModel.isShowNewGroupDialog = false
        homeViewModel.apiKeyText = ""
        keyboardController?.hide()
      },
      containerColor = LightBgColor,
      title = {
        Text("New Group", style = MaterialTheme.typography.titleSmall, color = Color.White)
      },
      text = {
        TextInput(
          value = homeViewModel.groupNameText,
          onValueChange = { homeViewModel.groupNameText = it },
          placeholder = "Enter the Chat Name",
          bgColor = BlackLight,
        )
      },
    )
  }
}
