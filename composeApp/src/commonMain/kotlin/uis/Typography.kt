package configs.uis

import allaichat.composeapp.generated.resources.*
import allaichat.composeapp.generated.resources.Res
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font

val popinsFontFamily
  @Composable get() =
    FontFamily(
      Font(
        Res.font.poppins_regular,
        FontWeight.Normal,
        FontStyle.Normal,
      ),
      Font(
        Res.font.poppins_medium,
        FontWeight.Medium,
        FontStyle.Normal,
      ),
      Font(
        Res.font.poppins_semibold,
        FontWeight.SemiBold,
        FontStyle.Normal,
      ),
      Font(
        Res.font.poppins_bold,
        FontWeight.Bold,
        FontStyle.Normal,
      ),
    )

val Typography
  @Composable
  get() =
    Typography(
      displayMedium =
        TextStyle(
          fontSize = 30.sp,
          fontWeight = FontWeight.SemiBold,
          fontFamily = popinsFontFamily,
        ),
      headlineLarge =
        TextStyle(
          fontSize = 29.sp,
          fontWeight = FontWeight.Bold,
          fontFamily = popinsFontFamily,
        ),
      titleMedium =
        TextStyle(
          fontSize = 20.sp,
          fontWeight = FontWeight.SemiBold,
          fontFamily = popinsFontFamily,
        ),
      titleSmall =
        TextStyle(
          fontSize = 18.sp,
          fontWeight = FontWeight.SemiBold,
          fontFamily = popinsFontFamily,
        ),
      bodyMedium =
        TextStyle(
          fontSize = 16.sp,
          fontWeight = FontWeight.Normal,
          fontFamily = popinsFontFamily,
        ),
      bodySmall =
        TextStyle(
          fontSize = 14.sp,
          fontWeight = FontWeight.Normal,
          fontFamily = popinsFontFamily,
        ),
      labelMedium =
        TextStyle(
          fontSize = 12.sp,
          fontWeight = FontWeight.Medium,
          fontFamily = popinsFontFamily,
        ),
    )
