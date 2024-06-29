package components.common

import androidx.compose.animation.core.ExperimentalTransitionApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.rememberTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.ContentScale
import coil3.ImageLoader
import coil3.SingletonImageLoader
import coil3.compose.AsyncImagePainter
import coil3.compose.DefaultModelEqualityDelegate
import coil3.compose.EqualityDelegate
import coil3.compose.LocalPlatformContext
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import utils.logging.AppLogger

@OptIn(ExperimentalTransitionApi::class)
@Composable
fun AsyncImage(
  model: Any?,
  contentDescription: String?,
  modifier: Modifier = Modifier,
  imageLoader: ImageLoader = SingletonImageLoader.get(LocalPlatformContext.current),
  transform: (AsyncImagePainter.State) -> AsyncImagePainter.State = AsyncImagePainter.DefaultTransform,
  onState: ((AsyncImagePainter.State) -> Unit)? = null,
  alignment: Alignment = Alignment.Center,
  contentScale: ContentScale = ContentScale.Fit,
  alpha: Float = DefaultAlpha,
  colorFilter: ColorFilter? = null,
  filterQuality: FilterQuality = DrawScope.DefaultFilterQuality,
  modelEqualityDelegate: EqualityDelegate = DefaultModelEqualityDelegate,
) {
  var loadStartTime by remember { mutableStateOf(Instant.DISTANT_PAST) }

  val transitionState =
    remember {
      MutableTransitionState<Pair<AsyncImagePainter.State, Instant>>(
        initialState = AsyncImagePainter.State.Empty to loadStartTime,
      )
    }
  val transition = rememberTransition(transitionState, "image fade")
  val imageTransition = transition.updateImageLoadingTransition()

  coil3.compose.AsyncImage(
    model = model,
    contentDescription = contentDescription,
    imageLoader = imageLoader,
    modifier = modifier,
    transform = { state ->
      transform(state).let { transformed ->
        AppLogger.d("[Start loading]")
        when (transformed) {
          is AsyncImagePainter.State.Loading -> {
            loadStartTime = Clock.System.now()
            transformed
          }

          is AsyncImagePainter.State.Success -> {
            AppLogger.d("[Start Success]")
            val newPainter =
              transformPainter(transformed.painter) {
                val cm = ColorMatrix()
                cm.apply {
                  setAlpha(imageTransition.alpha)
                  setBrightness(imageTransition.brightness)
                  setSaturation(imageTransition.saturation)
                }

                ColorFilter.colorMatrix(cm)
              }
            AsyncImagePainter.State.Success(newPainter, transformed.result)
          }

          else -> transformed
        }
      }.also {
        // Finally update the transition state
        transitionState.targetState = it to loadStartTime
        AppLogger.d("[Start Finally]")
      }
    },
    onState = onState,
    alignment = alignment,
    contentScale = contentScale,
    alpha = alpha,
    colorFilter = colorFilter,
    filterQuality = filterQuality,
    modelEqualityDelegate = modelEqualityDelegate,
  )
}
