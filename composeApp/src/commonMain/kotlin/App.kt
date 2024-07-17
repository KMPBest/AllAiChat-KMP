import androidx.compose.runtime.*
import configs.uis.MyAppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import screens.main.MainScreen
import screens.main.MainViewModel

@Composable
@Preview
fun App(mainViewModel: MainViewModel) {
  MyAppTheme {
    MainScreen(mainViewModel)
  }
}
