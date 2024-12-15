package fd.cmp.movie.screen.user

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fd.cmp.movie.data.model.User
import fd.cmp.movie.data.repository.UserRepository
import fd.cmp.movie.helper.TextHelper
import kotlinx.coroutines.launch

class UserViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {

    var state by mutableStateOf<UserDetailState>(UserDetailState.Loading)

    var stateEdit by mutableStateOf<UserEditState>(UserEditState.Idle)

    var isEdit by mutableStateOf(false)

    var user: User? = null

    var nameError by mutableStateOf(false)

    var phoneError by mutableStateOf(false)

    var dateOfBirthError by mutableStateOf(false)

    init {
        fetchUserDetail()
    }

    private fun fetchUserDetail() {
        viewModelScope.launch {
            val result = userRepository.getUserByEmail(userRepository.getEmail())
            if (result.isSuccess) {
                user = result.getOrNull()
                if (user == null) {
                    state = UserDetailState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
                    return@launch
                }
                user?.let { state = UserDetailState.Success(it) }
            } else if (result.isFailure) {
                state = UserDetailState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
            }
        }
    }

    private fun isValidRequest(): Boolean {
        nameError = user?.name.isNullOrEmpty()
        phoneError = user?.phone.isNullOrEmpty() || !TextHelper.isValidPhoneNumber(user?.phone)
        dateOfBirthError = user?.dateOfBirth.isNullOrEmpty() || !TextHelper.isValidDate(user?.dateOfBirth)
        return !nameError && !phoneError && !dateOfBirthError
    }

    fun editUser(user: User) {
        stateEdit = UserEditState.Loading
        if (!isValidRequest()) {
            stateEdit = UserEditState.Error("Please fill all required fields")
            return
        }
        viewModelScope.launch {
            userRepository.editUser(user)
            stateEdit = UserEditState.Success(user)
        }
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
        }
    }
}