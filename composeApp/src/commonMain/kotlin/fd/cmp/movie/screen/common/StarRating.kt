package fd.cmp.movie.screen.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter.Companion.tint
import androidx.compose.ui.unit.dp
import cmp_movie.composeapp.generated.resources.Res
import cmp_movie.composeapp.generated.resources.empty_star_icon_description
import cmp_movie.composeapp.generated.resources.full_star_icon_description
import cmp_movie.composeapp.generated.resources.half_star_icon_description
import cmp_movie.composeapp.generated.resources.ic_half_star
import cmp_movie.composeapp.generated.resources.ic_star
import cmp_movie.composeapp.generated.resources.ic_star_outline
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@Composable
fun StarRating(
    rating: Double,
    maxRating: Int = 10,
    starColor: Color = MaterialTheme.colorScheme.primary,
    starSize: Int = 18
) {
    val maxStars = 5
    val normalizedRating = (rating / maxRating) * maxStars
    val fullStars = normalizedRating.toInt()
    val hasHalfStar = (normalizedRating - fullStars) >= 0.5
    val emptyStars = maxStars - fullStars - if (hasHalfStar) 1 else 0

    Row {
        repeat(fullStars) {
            Image(
                painter = painterResource(Res.drawable.ic_star),
                contentDescription = stringResource(Res.string.full_star_icon_description),
                modifier = Modifier.size(starSize.dp),
                colorFilter = tint(starColor)
            )
        }
        if (hasHalfStar) {
            Image(
                painter = painterResource(Res.drawable.ic_half_star),
                contentDescription = stringResource(Res.string.half_star_icon_description),
                modifier = Modifier.size(starSize.dp),
                colorFilter = tint(starColor)
            )
        }
        repeat(emptyStars) {
            Image(
                painter = painterResource(Res.drawable.ic_star_outline),
                contentDescription = stringResource(Res.string.empty_star_icon_description),
                modifier = Modifier.size(starSize.dp),
                colorFilter = tint(starColor)
            )
        }
    }
}