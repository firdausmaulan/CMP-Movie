package fd.cmp.movie.screen.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

/**
 * A flexible, composable network image component that handles various image loading scenarios
 * using Coil in Kotlin Multiplatform Compose.
 *
 * @param imageUrl The URL of the image to be loaded
 * @param modifier Modifier to be applied to the image
 * @param contentDescription Accessibility content description
 * @param contentScale How the image should be scaled
 * @param placeholderRes Resource ID for placeholder image
 * @param errorRes Resource ID for error image
 * @param width Optional fixed width
 * @param height Optional fixed height
 * @param cornerRadius Corner radius for image
 * @param alpha Opacity of the image
 * @param blurRadius Blur radius to apply
 * @param colorFilter Optional color filter to apply
 */
@Composable
fun NetworkImage(
    imageUrl: String?,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop,
    placeholderRes: DrawableResource? = null,
    errorRes: DrawableResource? = null,
    width: Dp? = null,
    height: Dp? = null,
    cornerRadius: Dp = 0.dp,
    alpha: Float = 1f,
    blurRadius: Dp = 0.dp,
    colorFilter: ColorFilter? = null
) {
    // Determine the final modifier based on provided parameters
    val finalModifier = modifier
        .then(
            Modifier.apply {
                width?.let { width(it) }
                height?.let { height(it) }
            }
        )
        .clip(RoundedCornerShape(cornerRadius))
        .alpha(alpha)
        .blur(blurRadius)

    // Render the image
    AsyncImage(
        model = imageUrl,
        contentDescription = contentDescription,
        modifier = finalModifier,
        contentScale = contentScale,
        placeholder = placeholderRes?.let { painterResource(it) },
        error = errorRes?.let { painterResource(it) }
    )
}