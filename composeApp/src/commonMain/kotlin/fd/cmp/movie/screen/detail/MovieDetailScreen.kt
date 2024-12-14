package fd.cmp.movie.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cmp_movie.composeapp.generated.resources.Res
import cmp_movie.composeapp.generated.resources.compose_multiplatform
import cmp_movie.composeapp.generated.resources.genre
import cmp_movie.composeapp.generated.resources.ic_back
import cmp_movie.composeapp.generated.resources.rating
import cmp_movie.composeapp.generated.resources.release_date
import fd.cmp.movie.data.model.Movie
import fd.cmp.movie.helper.UiHelper
import fd.cmp.movie.screen.common.ErrorScreen
import fd.cmp.movie.screen.common.LoadingScreen
import fd.cmp.movie.screen.common.NetworkImage
import fd.cmp.movie.screen.common.StarRating
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    movieId: Int?,
    genres: String?,
    navigateBack: () -> Unit
) {

    val viewModel = koinViewModel<MovieDetailViewModel>()

    LaunchedEffect(movieId) {
        viewModel.loadMovie(movieId)
        viewModel.genres = genres.toString()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = UiHelper.topAppBarColors(),
                title = {
                    Text(
                        viewModel.blogTitle,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = viewModel.titleTextSizes.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navigateBack() }) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_back),
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(color = MaterialTheme.colorScheme.background),
        ) {
            when (val state = viewModel.state) {
                is MovieDetailState.Loading -> LoadingScreen()
                is MovieDetailState.Error -> ErrorScreen(subMessage = state.message)
                is MovieDetailState.Success -> BlogDetailView(state.movie, viewModel.genres)
            }
        }
    }
}

@Composable
fun BlogDetailView(movie: Movie, genres: String?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState()),
    ) {
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
                        text = "${stringResource(Res.string.release_date)} : ${movie.releaseDate}",
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    Row(modifier = Modifier.padding(horizontal = 8.dp)) {
                        Text(
                            text = "${stringResource(Res.string.rating)} : ${movie.formattedVoteAverage}",
                            color = Color.Gray,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        movie.voteAverage?.let { StarRating(it) }
                    }

                    Text(
                        text = "${stringResource(Res.string.genre)} : $genres",
                        maxLines = 1,
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
            }
        }

        Text(
            text = movie.overview.toString(),
            color = Color.Black,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(8.dp)
        )
    }
}