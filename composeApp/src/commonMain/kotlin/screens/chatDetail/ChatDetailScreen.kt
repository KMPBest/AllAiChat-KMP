package screens.chatDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import components.chatDetail.BottomChat
import components.chatDetail.MessageItem
import components.common.Header
import components.common.LoadingAnimation
import components.types.ImageType
import configs.uis.BlackLight
import dev.icerock.moko.permissions.compose.BindEffect
import di.toComposeImageBitmap
import screens.home.HomeViewModel
import utils.hideKeyboardOnOutsideClick
import viewModels.FilePickerUiState
import viewModels.FilePickerViewModel
import viewModels.PermissionsViewModel

@Composable
fun ChatDetailScreen(
  chatDetailViewModel: ChatDetailScreenViewModel,
  homeViewModel: HomeViewModel,
  permissionsViewModel: PermissionsViewModel,
  filePickerViewModel: FilePickerViewModel,
  groupId: String,
) {
  val chatUiState by chatDetailViewModel.chatUiState.collectAsState()
  val filePickerUiState by filePickerViewModel.uiState.collectAsState()

  chatDetailViewModel.groupId = groupId
  BindEffect(permissionsViewModel.getPermissionsController())
  ChatDetailScreen(
    chatDetailViewModel,
    homeViewModel,
    permissionsViewModel,
    filePickerViewModel,
    chatUiState,
    filePickerUiState,
  )
  LaunchedEffect(groupId) {
    chatDetailViewModel.getMessageList(true)
    chatDetailViewModel.getGroupDetail(groupId)
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatDetailScreen(
  chatViewModel: ChatDetailScreenViewModel,
  homeViewModel: HomeViewModel,
  permissionsViewModel: PermissionsViewModel,
  filePickerViewModel: FilePickerViewModel,
  chatUiState: ChatDetailUiState,
  filePickerUiState: FilePickerUiState,
) {
  val lazyListState = rememberLazyListState()
  val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

  Column(
    modifier =
      Modifier
        .fillMaxHeight()
        .nestedScroll(
          scrollBehavior.nestedScrollConnection,
        ).hideKeyboardOnOutsideClick()
        .systemBarsPadding(),
  ) {
    Header(
      title = chatUiState.groupDetail.groupName,
      rightIcon = ImageType.Vector(image = Icons.Filled.Delete),
      rightIconColor = Color.Red,
      rightIconModifier =
        Modifier.clickable {
          chatViewModel.openConfirmDeleteAlert()
        },
    )
    Row(
      Modifier.weight(1f).padding(horizontal = 12.dp),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      if (chatUiState.isLoading) {
        LoadingAnimation(Modifier.fillMaxWidth().wrapContentSize().padding(top = 24.dp))
      } else {
        LazyColumn(
          Modifier.fillMaxSize(),
          lazyListState,
          reverseLayout = true,
        ) {
          if (chatUiState.message.isNotEmpty()) {
            items(chatUiState.message) { message ->
              MessageItem(message)
            }
          } else {
            item {
              Text(
                text = "Empty",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
              )
            }
          }
        }
      }
    }
    LazyRow(Modifier.padding(horizontal = 12.dp)) {
      items(filePickerUiState.imageUris) { imageUri ->
        val bitmap = imageUri.toComposeImageBitmap()
        ImagePicker(bitmap = bitmap, onDelete = {
          filePickerViewModel.removeImage(imageUri)
        })
      }
    }
    BottomChat(chatViewModel, homeViewModel, permissionsViewModel, filePickerViewModel)
  }
}

@Composable
fun ImagePicker(
  bitmap: ImageBitmap?,
  onDelete: () -> Unit,
) {
  if (bitmap != null) {
    Box(
      modifier = Modifier.background(BlackLight, RoundedCornerShape(16.dp)),
    ) {
      Image(
        bitmap,
        contentDescription = null,
        contentScale = ContentScale.FillHeight,
        modifier = Modifier.padding(8.dp).height(150.dp).clip(RoundedCornerShape(12.dp)),
      )
      Icon(
        Icons.Default.Close,
        tint = Color.White,
        contentDescription = "remove",
        modifier =
          Modifier
            .padding(
              end = 8.dp,
              top = 8.dp,
            ).clip(CircleShape)
            .background(BlackLight)
            .align(Alignment.TopEnd)
            .clickable {
              onDelete()
            },
      )
    }
  }
}
