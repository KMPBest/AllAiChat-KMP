package screens.home

import allaichat.composeapp.generated.resources.Res
import allaichat.composeapp.generated.resources.menu
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import components.common.Header
import components.common.Wrapper
import components.types.ImageType
import navigation.NavControllerHolder
import navigation.Screens

@Composable
fun HomeScreen() {
  Wrapper {
    Header(
      leftIcon = ImageType.Resource(Res.drawable.menu),
      title = "Hi, Human\uD83D\uDC4B",
      rightIcon =
        ImageType.Url(
          "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcTZjFvVNnk5VWiX9iFt-XhZEagLnwvex31g036VxOU7qUBDB3p7edO2VdJllu2swpxX5t-9c9ah29A6jaCS31N-zySlnal1XSzgd0seAQs",
        ),
    )

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
  }
}
