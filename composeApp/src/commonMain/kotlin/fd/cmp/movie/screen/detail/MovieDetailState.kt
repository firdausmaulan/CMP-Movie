package fd.cmp.movie.screen.detail

import fd.cmp.movie.data.model.Movie

sealed class MovieDetailState {
    data object Loading : MovieDetailState()
    data class Error(val message: String) : MovieDetailState()
    data class Success(val movie: Movie) : MovieDetailState()
}