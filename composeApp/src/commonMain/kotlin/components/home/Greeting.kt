package components.home

import allaichat.composeapp.generated.resources.Res
import allaichat.composeapp.generated.resources.gemini_logo
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import org.jetbrains.compose.resources.painterResource

@Composable
fun Greeting() {
  Column(
    Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
  ) {
    Image(
      painter = painterResource(Res.drawable.gemini_logo),
      contentDescription = "Gemini logo",
    )
    Text(
      text = "Welcome to Allaichat.",
      style = MaterialTheme.typography.headlineLarge,
      textAlign = TextAlign.Center,
    )
    Text(
      text = "All you need here",
      style = MaterialTheme.typography.titleSmall,
      textAlign = TextAlign.Center,
    )
  }
}
