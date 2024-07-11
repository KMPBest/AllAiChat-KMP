package screens.home

import allaichat.composeapp.generated.resources.Res
import allaichat.composeapp.generated.resources.menu
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import components.common.Header
import components.home.Greeting
import components.types.ImageType
import configs.uis.BlackLight
import navigation.NavControllerHolder
import navigation.Screens

@Composable
fun HomeScreen() {
  val actionBtnOffset = DpOffset((-16).dp, (-16).dp)
  Column(
    Modifier.safeContentPadding(),
  ) {
    Header(
      leftIcon = ImageType.Resource(Res.drawable.menu),
      title = "Hi, Human\uD83D\uDC4B",
      rightIcon =
        ImageType.Url(
          "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcTZjFvVNnk5VWiX9iFt-XhZEagLnwvex31g036VxOU7qUBDB3p7edO2VdJllu2swpxX5t-9c9ah29A6jaCS31N-zySlnal1XSzgd0seAQs",
        ),
    )

    Column(
      modifier = Modifier.weight(1f),
    ) {
      Button(
        onClick = {
          NavControllerHolder.navController.navigate(Screens.ChatDetail)
        },
      ) {
        Text(
          text = "Go chat GPT",
          style = MaterialTheme.typography.headlineLarge,
        )
      }

      Greeting()

      Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd,
      ) {
        ExtendedFloatingActionButton(
          onClick = {},
          icon = { Icon(Icons.Filled.Add, "") },
          text = { Text(text = "New Group") },
          modifier = Modifier.padding(16.dp),
          containerColor = BlackLight,
        )
      }
    }
  }
}
