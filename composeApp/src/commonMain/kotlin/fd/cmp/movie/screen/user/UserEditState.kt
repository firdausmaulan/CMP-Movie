package fd.cmp.movie.screen.user

import fd.cmp.movie.data.model.User

sealed class UserEditState {
    data object Idle : UserEditState()
    data object Loading : UserEditState()
    data class Error(val message: String) : UserEditState()
    data class Success(val user: User) : UserEditState()
}