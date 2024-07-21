package screens.home

import allaichat.composeapp.generated.resources.Res
import allaichat.composeapp.generated.resources.menu
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import components.common.Header
import components.home.Greeting
import components.home.GroupList
import components.main.Alert
import components.main.ApiKeyDialog
import components.main.NewChatDialog
import components.types.ImageType
import configs.uis.BlackLight
import screens.main.MainViewModel

@Composable
fun HomeScreen(
  homeViewModel: HomeViewModel,
  mainViewModel: MainViewModel,
) {
  val chatUiState by homeViewModel.groupUiState.collectAsState()
  HomeScreen(homeViewModel, mainViewModel, chatUiState)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
  homeViewModel: HomeViewModel,
  mainViewModel: MainViewModel,
  homeUiState: HomeUiState,
) {
  val actionBtnOffset = DpOffset((-16).dp, (-16).dp)

//  AppLogger.e("[TOP] ${systemBarPaddingValues.getTop}")

  val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

  ApiKeyDialog(homeViewModel)
  Alert(homeViewModel)
  NewChatDialog(homeViewModel)

  Column(
    Modifier.fillMaxSize().systemBarsPadding(),
  ) {
    Header(
      leftIcon = ImageType.Resource(Res.drawable.menu),
      title = "Hi, Human\uD83D\uDC4B",
      rightIcon =
        ImageType.Url(
          "https://pics.craiyon.com/2023-10-12/056e9b9c2158492fbbe042117671b435.webp",
        ),
    )

    Column(
      modifier = Modifier.weight(1f),
    ) {
      if (homeUiState.group.isNotEmpty()) {
        GroupList(homeUiState)
      } else {
        Greeting()
      }
    }

    Box(
      Modifier.fillMaxWidth(),
      contentAlignment = Alignment.BottomEnd,
    ) {
      ExtendedFloatingActionButton(
        onClick = {
          homeViewModel.onNewGroup()
        },
        icon = { Icon(Icons.Filled.Add, "") },
        text = { Text(text = "New Group") },
        modifier = Modifier.padding(16.dp).align(Alignment.BottomEnd),
        containerColor = BlackLight,
      )
    }
  }
}
