package fd.cmp.movie.screen.user

import fd.cmp.movie.data.model.User

sealed class UserDetailState {
    data object Loading : UserDetailState()
    data class Error(val message: String) : UserDetailState()
    data class Success(val user: User) : UserDetailState()
}