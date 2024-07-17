package components.home

import allaichat.composeapp.generated.resources.*
import allaichat.composeapp.generated.resources.Res
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import configs.uis.LightBgColor
import domain.model.Group
import navigation.NavControllerHolder
import navigation.Screens
import org.jetbrains.compose.resources.painterResource
import screens.home.HomeUiState

@Composable
fun GroupList(homeUiState: HomeUiState) {
  LazyColumn(
    Modifier.fillMaxSize(),
  ) {
    if (homeUiState.group.isNotEmpty()) {
      items(homeUiState.group) { GroupItem(it) }
    } else {
      item {
        Text(
          text = "loading",
        )
      }
    }
  }
}

@Composable
fun GroupItem(group: Group) {
  val groupIcon =
    when (group.groupIcon) {
      "robot_1.png" -> Res.drawable.robot_1
      "robot_2.png" -> Res.drawable.robot_2
      "robot_3.png" -> Res.drawable.robot_3
      "robot_4.png" -> Res.drawable.robot_4
      "robot_5.png" -> Res.drawable.robot_5
      else -> Res.drawable.robot_6
    }
  Card(
    modifier = Modifier.fillMaxWidth().padding(8.dp),
    colors =
      CardDefaults.cardColors(
        containerColor = LightBgColor,
      ),
    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
    onClick = {
      NavControllerHolder.navController.navigate(Screens.ChatDetail.replace("{groupId}", group.groupId))
    },
  ) {
    Row(Modifier.padding(12.dp)) {
      Image(
        painter = painterResource(groupIcon),
        null,
        modifier = Modifier.size(50.dp),
      )
      Column(Modifier.padding(start = 12.dp)) {
        Text(
          group.groupName,
          style = MaterialTheme.typography.bodyMedium,
        )
        Text(
          group.create,
        )
      }
    }
  }
}
