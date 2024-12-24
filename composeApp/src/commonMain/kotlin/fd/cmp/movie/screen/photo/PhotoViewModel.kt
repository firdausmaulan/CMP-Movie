package fd.cmp.movie.screen.photo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fd.cmp.movie.data.remote.api.core.ApiResponse
import fd.cmp.movie.data.repository.UserRepository
import kotlinx.coroutines.launch

class PhotoViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {

    var state by mutableStateOf<PhotoState>(PhotoState.Idle)

    fun uploadPhoto(photo: ByteArray?) {
        state = PhotoState.Loading
        viewModelScope.launch {
            val result = userRepository.uploadPhoto(photo)
            if (result is ApiResponse.Success) {
                state = if (result.data.imagePath.isNullOrEmpty()) {
                    PhotoState.Error("Image path is empty")
                } else {
                    PhotoState.Success(result.data.imagePath)
                }
            } else if (result is ApiResponse.Error) {
                state = PhotoState.Error(result.message)
            }
        }
    }

}