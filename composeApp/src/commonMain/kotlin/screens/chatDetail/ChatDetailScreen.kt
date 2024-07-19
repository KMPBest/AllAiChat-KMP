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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import components.chatDetail.BottomChat
import components.chatDetail.MessageItem
import components.common.Header
import components.common.LoadingAnimation
import configs.uis.BlackLight
import di.toComposeImageBitmap
import screens.home.HomeViewModel
import utils.hideKeyboardOnOutsideClick
import viewModels.FilePickerModel

@Composable
fun ChatDetailScreen(
  chatDetailViewModel: ChatDetailScreenViewModel,
  homeViewModel: HomeViewModel,
  filePickerModel: FilePickerModel,
  groupId: String,
) {
  val chatUiState by chatDetailViewModel.chatUiState.collectAsState()
  chatDetailViewModel.groupId = groupId
  ChatDetailScreen(chatDetailViewModel, homeViewModel, filePickerModel, chatUiState)

  LaunchedEffect(groupId) {
    chatDetailViewModel.getMessageList(true)
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatDetailScreen(
  chatViewModel: ChatDetailScreenViewModel,
  homeViewModel: HomeViewModel,
  filePickerModel: FilePickerModel,
  chatUiState: ChatDetailUiState,
) {
  val lazyListState = rememberLazyListState()
  val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
  Column(
    modifier =
      Modifier.fillMaxHeight().nestedScroll(
        scrollBehavior.nestedScrollConnection,
      ).hideKeyboardOnOutsideClick().safeContentPadding(),
  ) {
    Header(title = "Chat detail")
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
      val bitmap = filePickerModel.uiState
      items(filePickerModel.imageUris) { imageUri ->
        val bitmap = imageUri.toComposeImageBitmap()
        Box(
          modifier = Modifier.padding(4.dp).background(BlackLight, RoundedCornerShape(10.dp)),
        ) {
          Image(
            bitmap,
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier.padding(4.dp).height(192.dp).clip(RoundedCornerShape(16.dp)),
          )
          Icon(
            Icons.Default.Close,
            tint = Color.White,
            contentDescription = "remove",
            modifier =
              Modifier.padding(
                end = 8.dp,
                top = 8.dp,
              ).clip(CircleShape).background(BlackLight).align(Alignment.TopEnd).clickable {
                filePickerModel.imageUris.remove(imageUri)
              },
          )
        }
      }
    }
    BottomChat(chatViewModel, homeViewModel, filePickerModel)
  }
}
