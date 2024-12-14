package fd.cmp.movie.screen.login

sealed class UserLoginState {
    data object Idle : UserLoginState()
    data object Loading : UserLoginState()
    data class Error(val message: String?) : UserLoginState()
    data object Success : UserLoginState()
}