package fd.cmp.movie.screen.photo

sealed class PhotoState {
    data object Idle : PhotoState()
    data object Loading : PhotoState()
    data class Error(val message: String?) : PhotoState()
    data class Success(val imagePath: String) : PhotoState()
}