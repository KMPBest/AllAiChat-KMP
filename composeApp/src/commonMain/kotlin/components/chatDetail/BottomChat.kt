package components.chatDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import components.form.TextInput
import configs.uis.BlackLight
import screens.chatDetail.ChatDetailScreenViewModel
import screens.home.HomeViewModel
import viewModels.FilePickerModel

@Composable
fun BottomChat(
  chatViewModel: ChatDetailScreenViewModel,
  homeViewModel: HomeViewModel,
  filePickerModel: FilePickerModel,
) {
  val controler = LocalSoftwareKeyboardController.current
  val focusManager = LocalFocusManager.current

  Row(Modifier.fillMaxWidth().padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
    IconButton(
      onClick = {
        filePickerModel.pickImage()
        controler?.hide()
        focusManager.clearFocus()
      },
      modifier = Modifier.padding(end = 12.dp).clip(RoundedCornerShape(100.dp)).background(BlackLight),
    ) {
      Icon(
        imageVector = Icons.Filled.Add,
        contentDescription = "send",
        tint = Color.White,
      )
    }
    TextInput(
      value = chatViewModel.message,
      onValueChange = {
        chatViewModel.message = it
      },
      modifier = Modifier.weight(1f),
      placeholder = "Type a message",
    )
    IconButton(
      onClick = {
        chatViewModel.generateContentWithText(
          groupId = chatViewModel.groupId,
          chatViewModel.message,
          homeViewModel.apiKeyText,
          filePickerModel.imageUris,
        )
        filePickerModel.imageUris.clear()
        controler?.hide()
        focusManager.clearFocus()
      },
      modifier = Modifier.padding(start = 12.dp).clip(RoundedCornerShape(100.dp)).background(BlackLight),
    ) {
      Icon(
        imageVector = Icons.AutoMirrored.Filled.Send,
        contentDescription = "send",
        tint = Color.White,
      )
    }
  }
}
