package screens.chatDetail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import components.chatDetail.MessageItem
import components.common.Header
import components.form.TextInput
import utils.hideKeyboardOnOutsideClick
import utils.logging.AppLogger

@Composable
fun ChatDetailScreen(
  chatDetailViewModel: ChatDetailScreenViewModel,
  groupId: String,
) {
  val chatUiState by chatDetailViewModel.chatUiState.collectAsState()
  AppLogger.e("[groupId] $groupId")
  chatDetailViewModel.groupId = groupId
  chatDetailViewModel.getMessageList()
  ChatDetailScreen(chatDetailViewModel, chatUiState, groupId)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatDetailScreen(
  chatViewModel: ChatDetailScreenViewModel,
  chatUiState: ChatDetailUiState,
  groudId: String? = null,
) {
  val controler = LocalSoftwareKeyboardController.current
  val focusManager = LocalFocusManager.current

  val lazyListState = rememberLazyListState()
  val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
  Column(
    modifier =
      Modifier.fillMaxHeight().nestedScroll(
        scrollBehavior.nestedScrollConnection,
      ).hideKeyboardOnOutsideClick().safeContentPadding().padding(horizontal = 12.dp),
  ) {
    Header(title = "Chat detail")
    Row(Modifier.weight(1f)) {
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
            if (chatUiState.isLoading)
              {
                Text(
                  text = "Loading",
                  textAlign = TextAlign.Center,
                  modifier = Modifier.fillMaxWidth(),
                )
              } else {
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

    Row(Modifier.fillMaxWidth().padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
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
            "AIzaSyDQebPNS2CokHujDgVBVRnw9_eZgY-i1m8",
          )
          controler?.hide()
          focusManager.clearFocus()
        },
      ) {
        Icon(
          imageVector = Icons.AutoMirrored.Filled.Send,
          contentDescription = "send",
          tint = Color.White,
        )
      }
    }
  }
}
