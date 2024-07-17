import androidx.compose.ui.window.ComposeUIViewController
import org.koin.mp.KoinPlatform
import screens.main.MainViewModel

fun MainViewController() =
  ComposeUIViewController {
    val mainViewModel: MainViewModel = KoinPlatform.getKoin().get()
    App(mainViewModel)
  }
