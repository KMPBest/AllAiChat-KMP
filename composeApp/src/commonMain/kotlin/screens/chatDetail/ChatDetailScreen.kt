package screens.chatDetail

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import components.common.Wrapper
import components.form.TextInput
import navigation.NavControllerHolder
import org.koin.compose.koinInject

// @Composable
// fun ChatDetailScreen(uiStateHolder: ChatDetailScreenViewModel) {
//  val chatDetailViewModel = koinInject<ChatDetailScreenViewModel>()
// //  val chatDetail by chatDetailViewModel.
// //    .chatDetailViewModel
// //    .ChatDetailScreen()
// }

@Composable
fun ChatDetailScreen(uiStateHolder: ChatDetailScreenViewModel = koinInject()) {
//  val chatDetailViewModel = koinInject<ChatDetailScreenViewModel>()

  Wrapper {
    Text(
      text = "Chat Detail",
      style = MaterialTheme.typography.headlineLarge,
    )
    Row {
      TextInput(
        value = uiStateHolder.searchQuery.value,
        onValueChange = {
          uiStateHolder.onSearchQueryChange(it)
        },
        modifier = Modifier.weight(1f),
      )
      Button(
        onClick = {
          NavControllerHolder.navController.popBackStack()
        },
      ) {
        Text(
          text = "Go back",
          style = MaterialTheme.typography.headlineLarge,
        )
      }
    }
  }
}
