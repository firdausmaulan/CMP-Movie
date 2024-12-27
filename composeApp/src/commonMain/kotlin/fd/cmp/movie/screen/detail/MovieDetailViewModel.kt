package fd.cmp.movie.screen.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fd.cmp.movie.data.remote.api.core.ApiResponse
import fd.cmp.movie.data.repository.MovieRepository
import fd.cmp.movie.helper.TextHelper
import kotlinx.coroutines.launch

class MovieDetailViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    var state by mutableStateOf<MovieDetailState>(MovieDetailState.Loading)

    fun loadMovie(id: Int?) {
        viewModelScope.launch {
            val result = movieRepository.detail(id)
            if (result is ApiResponse.Success) {
                val cast = result.data.cast?.take(12)
                state = MovieDetailState.Success(result.data.copy(cast = cast))
            } else if (result is ApiResponse.Error) {
                state = MovieDetailState.Error(result.message)
            }
        }
    }
}