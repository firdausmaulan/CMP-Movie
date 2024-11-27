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
import coil3.compose.AsyncImage
import fd.cmp.movie.data.model.Movie
import org.jetbrains.compose.resources.painterResource

@Composable
fun MovieItem(item: Movie, onItemClick: (movie: Movie) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        onClick = { onItemClick(item) },
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = item.title.toString(),
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Box(modifier = Modifier.fillMaxWidth()) {
                AsyncImage(
                    model = item.backdropPathUrl,
                    contentDescription = item.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .fillMaxWidth()
                        .height(200.dp)
                        .blur(radius = 10.dp)
                        .alpha(0.8f)
                )

                AsyncImage(
                    model = item.posterPathUrl,
                    contentDescription = item.title,
                    modifier = Modifier.size(150.dp, 200.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .align(Alignment.Center),
                    contentScale = ContentScale.FillBounds,
                    error = painterResource(Res.drawable.compose_multiplatform),
                    placeholder = painterResource(Res.drawable.compose_multiplatform)
                )
            }
        }
    }
}