import androidx.compose.runtime.*
import configs.uis.MyAppTheme
import navigation.RootNavigation
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
  MyAppTheme {
    RootNavigation()
  }
}
