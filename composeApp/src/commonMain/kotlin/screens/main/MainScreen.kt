package screens.main

import androidx.compose.runtime.Composable
import components.global.alert.Alert
import components.global.alert.AlertViewModel
import navigation.RootNavigation
import org.koin.mp.KoinPlatform

@Composable
fun MainScreen(mainViewModel: MainViewModel) {
  val alertViewModel: AlertViewModel = KoinPlatform.getKoin().get()
  Alert(alertViewModel)
  RootNavigation(mainViewModel)
}
