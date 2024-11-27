package fd.cmp.movie.screen.list

import fd.cmp.movie.data.model.Movie

sealed class MovieListState {
    data object Loading : MovieListState()
    data object Empty : MovieListState()
    data class Error(val message: String) : MovieListState()
    data class Success(val movies: List<Movie>, val isLoadMore: Boolean) : MovieListState()
}