package fd.cmp.movie.screen.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cmp_movie.composeapp.generated.resources.Res
import cmp_movie.composeapp.generated.resources.compose_multiplatform
import fd.cmp.movie.data.model.Movie
import fd.cmp.movie.screen.common.NetworkImage

@Composable
fun MovieItem(item: Movie, onItemClick: (movie: Movie) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        onClick = { onItemClick(item) },
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.fillMaxWidth()) {
                NetworkImage(
                    imageUrl = item.backdropPathUrl,
                    contentDescription = item.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                        .blur(radius = 10.dp)
                        .alpha(0.8f),
                )

                NetworkImage(
                    imageUrl = item.posterPathUrl,
                    contentDescription = item.title,
                    modifier = Modifier.size(150.dp, 200.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .align(Alignment.Center),
                    contentScale = ContentScale.FillBounds,
                    errorRes = Res.drawable.compose_multiplatform,
                    placeholderRes = Res.drawable.compose_multiplatform,
                )
            }

            Text(
                text = item.title.toString(),
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                color = MaterialTheme.colorScheme.background,
                fontWeight = FontWeight.W500,
                fontSize = 16.sp
            )
        }
    }
}