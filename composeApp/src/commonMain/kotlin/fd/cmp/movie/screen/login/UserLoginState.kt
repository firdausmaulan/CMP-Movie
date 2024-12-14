package fd.cmp.movie.screen.login

import com.fd.myblog.data.model.User

sealed class UserLoginState {
    data object Idle : UserLoginState()
    data object Loading : UserLoginState()
    data class Error(val message: String) : UserLoginState()
    data class Success(val user: User) : UserLoginState()
}