package fd.cmp.movie.screen.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cmp_movie.composeapp.generated.resources.Res
import cmp_movie.composeapp.generated.resources.back_icon_description
import cmp_movie.composeapp.generated.resources.compose_multiplatform
import cmp_movie.composeapp.generated.resources.genre_label
import cmp_movie.composeapp.generated.resources.ic_back
import cmp_movie.composeapp.generated.resources.rating_label
import cmp_movie.composeapp.generated.resources.release_date_label
import fd.cmp.movie.data.model.Movie
import fd.cmp.movie.screen.common.ErrorScreen
import fd.cmp.movie.screen.common.LoadingScreen
import fd.cmp.movie.screen.common.NetworkImage
import fd.cmp.movie.screen.common.StarRating
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MovieDetailScreen(
    movieId: Int?,
    navigateBack: () -> Unit
) {
    val viewModel = koinViewModel<MovieDetailViewModel>()

    LaunchedEffect(movieId) {
        viewModel.loadMovie(movieId)
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(color = MaterialTheme.colorScheme.background),
        ) {
            when (val state = viewModel.state) {
                is MovieDetailState.Loading -> LoadingScreen()
                is MovieDetailState.Error -> ErrorScreen(subMessage = state.message)
                is MovieDetailState.Success -> BlogDetailView(
                    state.movie,
                    navigateBack
                )
            }
        }
    }
}

@Composable
fun BlogDetailView(movie: Movie, navigateBack: () -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        item {
            Box(modifier = Modifier.fillMaxWidth().height(320.dp)) {
                // Backdrop Image
                NetworkImage(
                    imageUrl = movie.backdropPathUrl,
                    contentDescription = movie.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    contentScale = ContentScale.Crop,
                )

                // Back Button
                Box(
                    Modifier.padding(10.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    Image(
                        painter = painterResource(Res.drawable.ic_back),
                        contentDescription = stringResource(Res.string.back_icon_description),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                        modifier = Modifier
                            .padding(4.dp)
                            .size(24.dp)
                            .clickable {
                                navigateBack()
                            },
                    )
                }

                Row(modifier = Modifier.fillMaxWidth().padding(8.dp).align(Alignment.BottomStart)) {
                    NetworkImage(
                        imageUrl = movie.posterPathUrl,
                        contentDescription = movie.title,
                        modifier = Modifier
                            .size(100.dp, 150.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.FillBounds,
                        errorRes = Res.drawable.compose_multiplatform,
                        placeholderRes = Res.drawable.compose_multiplatform
                    )

                    Column(modifier = Modifier.align(Alignment.Bottom)) {
                        Text(
                            text = "${stringResource(Res.string.release_date_label)} : ${movie.releaseDate}",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )

                        Row(modifier = Modifier.padding(horizontal = 8.dp)) {
                            Text(
                                text = "${stringResource(Res.string.rating_label)} : ${movie.formattedVoteAverage}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            movie.voteAverage?.let { StarRating(it) }
                        }

                        Text(
                            text = "${stringResource(Res.string.genre_label)} : ${movie.formattedGenreNames}",
                            maxLines = 1,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }
                }
            }
        }

        item {
            Text(
                text = movie.title.toString(),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(8.dp)
            )
        }

        item {
            Text(
                text = movie.overview.toString(),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(8.dp)
            )
        }

        // Cast grid
        item {
            movie.cast?.let { castList ->
                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    modifier = Modifier
                        .padding(8.dp)
                        .height((castList.size / 4 * 100 + 100).dp) // Calculate height based on number of rows needed
                ) {
                    items(castList.size) { index ->
                        val cast = castList[index]
                        Box(
                            modifier = Modifier
                                .size(124.dp)
                                .padding(4.dp),
                        ) {
                            NetworkImage(
                                imageUrl = cast?.profileUrl,
                                contentDescription = cast?.name,
                                modifier = Modifier.size(80.dp)
                                    .clip(CircleShape)
                                    .border(
                                        width = 2.dp,
                                        color = MaterialTheme.colorScheme.primary,
                                        shape = CircleShape
                                    ),
                            )
                            Text(
                                text = cast?.name.toString(),
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.align(Alignment.BottomCenter).height(40.dp)
                                    .padding(top = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}