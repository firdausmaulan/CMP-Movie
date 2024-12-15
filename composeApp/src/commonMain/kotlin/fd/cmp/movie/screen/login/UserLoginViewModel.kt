package fd.cmp.movie.screen.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fd.cmp.movie.data.remote.api.core.ApiResponse
import fd.cmp.movie.data.remote.request.UserLoginRequest
import fd.cmp.movie.data.repository.UserRepository
import fd.cmp.movie.helper.TextHelper
import kotlinx.coroutines.launch

class UserLoginViewModel(
    private val repository: UserRepository
) : ViewModel() {

    var state by mutableStateOf<UserLoginState>(UserLoginState.Idle)

    var emailError by mutableStateOf(false)

    var passwordError by mutableStateOf(false)

    init {
        if (repository.getToken().isNotEmpty()) {
            state = UserLoginState.Success
        }
    }

    private fun isValidRequest(request: UserLoginRequest): Boolean {
        if (request.email == null || request.password == null) return false
        return TextHelper.isValidEmailFormat(request.email) && TextHelper.isValidPassword(request.password)
    }

    fun login(request: UserLoginRequest) {
        if (!isValidRequest(request)) return
        viewModelScope.launch {
            state = UserLoginState.Loading
            val result = repository.login(request)
            if (result is ApiResponse.Success) {
                state = UserLoginState.Success
                repository.saveToken(result.data.user?.token)
                repository.saveEmail(result.data.user?.email)
                repository.insertUser(result.data.user)
            } else if (result is ApiResponse.Error) {
                state = UserLoginState.Error(result.message)
            }
        }
    }
}
