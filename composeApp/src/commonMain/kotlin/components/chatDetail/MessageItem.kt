package components.chatDetail

import androidx.compose.animation.core.EaseOutSine
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import configs.uis.BlackLight
import domain.model.ChatMessage
import domain.model.Role

@Composable
fun MessageItem(
  chatMessage: ChatMessage,
  modifier: Modifier = Modifier,
) {
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
        modifier = Modifier.padding(8.dp),
      ) {
        Text(
          text = chatMessage.text,
        )
      }
    }
  }
}
