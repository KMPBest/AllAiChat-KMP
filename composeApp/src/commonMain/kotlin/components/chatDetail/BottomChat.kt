package components.chatDetail

import allaichat.composeapp.generated.resources.Res
import allaichat.composeapp.generated.resources.camera
import allaichat.composeapp.generated.resources.picture
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import components.form.TextInput
import configs.uis.BlackLight
import dev.icerock.moko.permissions.Permission
import di.rememberCameraManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.painterResource
import screens.chatDetail.ChatDetailScreenViewModel
import screens.home.HomeViewModel
import viewModels.FilePickerViewModel
import viewModels.PermissionsViewModel

@Composable
fun BottomChat(
  chatViewModel: ChatDetailScreenViewModel,
  homeViewModel: HomeViewModel,
  permissionsViewModel: PermissionsViewModel,
  filePickerModel: FilePickerViewModel,
) {
  val controler = LocalSoftwareKeyboardController.current
  val focusManager = LocalFocusManager.current
  val coroutineScope = rememberCoroutineScope()

  val cameraManager =
    rememberCameraManager {
      coroutineScope.launch {
        val bitmap =
          withContext(Dispatchers.Default) {
            it?.toByteArray()
          }
        if (bitmap != null) {
          filePickerModel.addImage(bitmap)
        }
      }
    }

  Row(
    Modifier.fillMaxWidth().padding(8.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Row(Modifier.animateContentSize(animationSpec = tween(durationMillis = 300))) {
      if (chatViewModel.bottomExpanded) {
        IconItem(
          painter = painterResource(Res.drawable.camera),
          contentDescription = "add",
          onClick = {
            permissionsViewModel.requestPermission(
              Permission.CAMERA,
              errorTitle = "Photo permission",
              errorMsg = "Photo permission is required for this app to work properly",
              onGranted = {
                cameraManager.launch()
              },
            )
          },
          modifier = Modifier.padding(end = 12.dp),
        )
        IconItem(
          painter = painterResource(Res.drawable.picture),
          contentDescription = "picture",
          onClick = {
            filePickerModel.pickImage()
            controler?.hide()
            focusManager.clearFocus()
          },
          modifier = Modifier.padding(end = 12.dp),
        )
      } else {
        IconItem(
          imageVector = Icons.AutoMirrored.Filled.ArrowForward,
          contentDescription = "picture",
          onClick = {
            chatViewModel.bottomExpanded = !chatViewModel.bottomExpanded
          },
          modifier = Modifier.padding(end = 12.dp).background(Color.Transparent),
          bgColor = Color.Transparent,
        )
      }
    }
    TextInput(
      value = chatViewModel.message,
      onValueChange = {
        chatViewModel.message = it
      },
      modifier = Modifier.weight(1f),
      modifierInput =
        Modifier.onFocusChanged { focusState ->
          chatViewModel.bottomExpanded = !focusState.isFocused
        },
      placeholder = "Type a message",
    )

    IconItem(
      imageVector = Icons.AutoMirrored.Filled.Send,
      contentDescription = "send",
      onClick = {
        chatViewModel.generateContentWithText(
          groupId = chatViewModel.groupId,
          chatViewModel.message,
          homeViewModel.apiKeyText,
          filePickerModel.uiState.value.imageUris,
        )
        filePickerModel.clearAllImages()
        controler?.hide()
        focusManager.clearFocus()
      },
      modifier = Modifier.padding(start = 12.dp),
    )
  }
}

@Composable
fun IconItem(
  painter: Painter? = null,
  imageVector: ImageVector? = null,
  contentDescription: String,
  tint: Color = Color.White,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  bgColor: Color = BlackLight,
) {
  IconButton(
    onClick = {
      onClick()
    },
    modifier = modifier.clip(CircleShape).background(bgColor),
  ) {
    if (painter != null) {
      Icon(
        painter = painter,
        contentDescription = contentDescription,
        tint = tint,
      )
    }

    if (imageVector != null) {
      Icon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        tint = tint,
      )
    }
  }
}
