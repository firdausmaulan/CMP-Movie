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
    var blogTitle by mutableStateOf("Detail")
    var titleTextSizes by mutableStateOf(TextHelper.setTextSizeBasedOnText(blogTitle))
    var genres by mutableStateOf("")

    fun loadMovie(id: Int?) {
        viewModelScope.launch {
            val result = movieRepository.detail(id)
            if (result is ApiResponse.Success) {
                state = MovieDetailState.Success(result.data)
                blogTitle = TextHelper.formatTitle(result.data.title.toString())
                titleTextSizes = TextHelper.setTextSizeBasedOnText(blogTitle)
            } else if (result is ApiResponse.Error) {
                state = MovieDetailState.Error(result.message)
            }
        }
    }
}