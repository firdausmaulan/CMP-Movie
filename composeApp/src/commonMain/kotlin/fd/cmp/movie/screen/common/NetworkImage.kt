package fd.cmp.movie.screen.common

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cmp_movie.composeapp.generated.resources.Res
import cmp_movie.composeapp.generated.resources.compose_multiplatform
import coil3.compose.AsyncImage
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
    placeholderRes: DrawableResource = Res.drawable.compose_multiplatform,
    errorRes: DrawableResource = Res.drawable.compose_multiplatform,
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
        placeholder = painterResource(placeholderRes),
        error = painterResource(errorRes)
    )
}