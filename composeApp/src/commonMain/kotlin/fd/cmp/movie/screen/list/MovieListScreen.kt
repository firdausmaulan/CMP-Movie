package fd.cmp.movie.screen.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cmp_movie.composeapp.generated.resources.Res
import cmp_movie.composeapp.generated.resources.app_name
import fd.cmp.movie.data.model.Movie
import fd.cmp.movie.helper.UiHelper
import fd.cmp.movie.screen.common.DebounceTextField
import fd.cmp.movie.screen.common.EmptyScreen
import fd.cmp.movie.screen.common.ErrorScreen
import fd.cmp.movie.screen.common.LoadingScreen
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListScreen(
    navigateToDetails: (objectId: Int?, genres: String?) -> Unit
) {
    val viewModel = koinViewModel<MovieListViewModel>()
    val pullRefreshState = rememberPullToRefreshState()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = UiHelper.topAppBarColors(),
                title = {
                    Text(
                        stringResource(Res.string.app_name),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pullToRefresh(
                    state = pullRefreshState,
                    isRefreshing = viewModel.isRefreshing,
                    onRefresh = { viewModel.reloadBlogs() }
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(color = MaterialTheme.colorScheme.background)
            ) {
                Box(Modifier.padding(8.dp)) {
                    DebounceTextField { viewModel.search(it) }
                }
                when (val state = viewModel.state) {
                    is MovieListState.Loading -> LoadingScreen()
                    is MovieListState.Empty -> EmptyScreen()
                    is MovieListState.Error -> ErrorScreen(subMessage = state.message)
                    is MovieListState.Success ->
                        Box(modifier = Modifier.fillMaxSize()) {
                            BlogListView(
                                movies = state.movies,
                                onLoadMore = { viewModel.loadMovies(true) },
                                onItemClick = { movie ->
                                    navigateToDetails(
                                        movie.id,
                                        movie.formattedGenreNames
                                    )
                                }
                            )
                            PullToRefreshBox(
                                isRefreshing = viewModel.isRefreshing,
                                onRefresh = {},
                                state = pullRefreshState,
                                modifier = Modifier.align(Alignment.TopCenter),
                                content = {}
                            )
                        }
                }
            }
        }
    }
}

@Composable
fun BlogListView(
    movies: List<Movie>,
    onLoadMore: () -> Unit,
    onItemClick: (movie: Movie) -> Unit
) {
    val listState = rememberLazyListState()

    val reachedBottom: Boolean by remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem?.index != 0 && lastVisibleItem?.index == listState.layoutInfo.totalItemsCount - 1
        }
    }

    LaunchedEffect(reachedBottom) {
        if (reachedBottom) onLoadMore()
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = listState
    ) {
        items(movies) { movie ->
            MovieItem(movie, onItemClick)
        }
    }
}