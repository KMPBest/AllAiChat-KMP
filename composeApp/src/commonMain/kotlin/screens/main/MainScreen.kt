package screens.main

import androidx.compose.runtime.Composable
import navigation.RootNavigation

@Composable
fun MainScreen(mainViewModel: MainViewModel) {
  RootNavigation(mainViewModel)
}
