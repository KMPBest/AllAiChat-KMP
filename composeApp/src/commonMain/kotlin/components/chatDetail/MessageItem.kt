package components.chatDetail

import allaichat.composeapp.generated.resources.Res
import allaichat.composeapp.generated.resources.ic_paste
import androidx.compose.animation.core.EaseOutSine
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.unit.dp
import configs.uis.BlackLight
import configs.uis.LightBgColor
import di.setClipData
import di.toComposeImageBitmap
import domain.model.ChatMessage
import domain.model.Role
import org.jetbrains.compose.resources.painterResource

@Composable
fun MessageItem(
  chatMessage: ChatMessage,
  modifier: Modifier = Modifier,
) {
  val clipboardManager = LocalClipboardManager.current
  val isMe = chatMessage.participant == Role.YOU
  val shape = RoundedCornerShape(10.dp)
  val bgColor =
    when (chatMessage.participant) {
      Role.CHAT -> BlackLight
      Role.YOU -> BlackLight
      Role.ERROR -> Red
    }

  val infiniteTransition = rememberInfiniteTransition(label = "")
  val rotate by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 360f,
    animationSpec =
      infiniteRepeatable(
        animation =
          tween(
            durationMillis = 1000,
            easing = EaseOutSine,
          ),
      ),
    label = "",
  )

  val circleColors =
    listOf(
      Color(0xFF5851D8),
      Color(0xFF833AB4),
      Color(0xFFC13584),
      Color(0xFFE1306C),
      Color(0xFFFD1D1D),
      Color(0xFFF56040),
      Color(0xFFF77737),
      Color(0xFFFCAF45),
      Color(0xFFFFDC80),
      Color(0xFF5851D8),
    )

  val cardShape =
    if (isMe) {
      RoundedCornerShape(
        16.dp,
        16.dp,
        0.dp,
        16.dp,
      )
    } else {
      RoundedCornerShape(
        16.dp,
        16.dp,
        16.dp,
        0.dp,
      )
    }

  Row(
    modifier =
      modifier.fillMaxWidth().padding(
        bottom = 16.dp,
        start = if (isMe) 24.dp else 0.dp,
        end = if (isMe) 0.dp else 24.dp,
      ),
    horizontalArrangement = if (isMe) Arrangement.End else Arrangement.Start,
  ) {
    Box(
      modifier =
        Modifier.clip(cardShape).padding(2.dp).drawWithContent {
          rotate(
            if (chatMessage.isPending) {
              rotate
            } else {
              0f
            },
          ) {
            drawCircle(
              brush =
                if (chatMessage.isPending && chatMessage.participant == Role.YOU) {
                  Brush.sweepGradient(circleColors)
                } else {
                  Brush.linearGradient(
                    listOf(
                      bgColor,
                      bgColor,
                    ),
                  )
                },
              radius = size.width,
              blendMode = BlendMode.SrcIn,
            )
          }
          drawContent()
        }.background(
          color = BlackLight,
          cardShape,
        ),
    ) {
      Column(
        modifier = Modifier.padding(8.dp).wrapContentSize(),
      ) {
        if (chatMessage.images.isNotEmpty()) {
          LazyRow(
            modifier = Modifier.wrapContentSize(),
            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.End),
          ) {
            items(chatMessage.images) {
              val bitmap = it.toComposeImageBitmap()
              Image(
                bitmap,
                contentDescription = null,
                modifier = Modifier.height(192.dp).clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.FillHeight,
              )
            }
          }
        }
        Text(
          text = chatMessage.text,
          modifier = Modifier.padding(top = 8.dp),
        )

        if (!isMe) {
          Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
          ) {
            Text(
              text = if (chatMessage.participant == Role.CHAT) "By AI" else "by Error",
              style = MaterialTheme.typography.titleSmall,
            )
            IconButton(
              onClick = {
                setClipData(clipboardManager, chatMessage.text)
              },
              colors =
                IconButtonDefaults.iconButtonColors(
                  containerColor = LightBgColor,
                ),
            ) {
              Icon(painterResource(Res.drawable.ic_paste), contentDescription = "copy")
            }
          }
        }
      }
    }
  }
}
