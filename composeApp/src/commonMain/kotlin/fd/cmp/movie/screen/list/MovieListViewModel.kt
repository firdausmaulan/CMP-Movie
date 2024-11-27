package fd.cmp.movie.screen.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fd.cmp.movie.data.remote.api.core.ApiResponse
import fd.cmp.movie.data.remote.request.MovieRequest
import fd.cmp.movie.data.repository.MovieRepository
import fd.cmp.movie.helper.Constants
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MovieListViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    var state by mutableStateOf<MovieListState>(MovieListState.Loading)
    var isRefreshing by mutableStateOf(false)
    private var currentPage = 1
    private var canLoadMore = true
    private var query = Constants.DEFAULT_QUERY

    fun search(query: String) {
        this.query = query
        currentPage = 1
        canLoadMore = true
        loadMovies()
    }

    fun reloadBlogs() {
        isRefreshing = true
        search(Constants.DEFAULT_QUERY)
        // delay 1 second to simulate refresh
        viewModelScope.launch {
            delay(1000)
            isRefreshing = false
        }
    }

    init {
        loadMovies()
    }

    fun loadMovies(isLoadMore: Boolean = false) {
        if (isLoadMore && !canLoadMore) return
        viewModelScope.launch {
            state = if (isLoadMore) MovieListState.Success(
                (state as? MovieListState.Success)?.movies.orEmpty(),
                true
            )
            else MovieListState.Loading
            val result = movieRepository.search(MovieRequest(query = query, page = currentPage))
            if (result is ApiResponse.Success) {
                val movies = result.data.results.orEmpty()
                if (isLoadMore) {
                    if (movies.isEmpty()) {
                        canLoadMore = false
                    } else {
                        val currentMovies = (state as? MovieListState.Success)?.movies.orEmpty()
                        state = MovieListState.Success(currentMovies + movies, true)
                        currentPage++
                        canLoadMore = movies.size >= 20
                    }
                } else {
                    if (movies.isEmpty()) {
                        state = MovieListState.Empty
                    } else {
                        currentPage++
                        state = MovieListState.Success(movies, false)
                    }
                }
            } else if (result is ApiResponse.Error) {
                state = MovieListState.Error(result.message)
            }
        }
    }
}