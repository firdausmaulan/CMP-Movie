package fd.cmp.movie.screen.user

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fd.cmp.movie.data.model.LocationData
import fd.cmp.movie.data.model.User
import fd.cmp.movie.data.repository.UserRepository
import fd.cmp.movie.helper.Constants
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

    var address by mutableStateOf("")

    var locationData by mutableStateOf<LocationData?>(null)

    var imageUrl by mutableStateOf("")

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
                kotlinx.coroutines.delay(1000)
                user?.let { state = UserDetailState.Success(it) }
                imageUrl = user?.imageUrl.toString()
                address = user?.address.toString()
                locationData = LocationData(
                    latitude = user?.latitude,
                    longitude = user?.longitude,
                    displayName = user?.address
                )
            } else if (result.isFailure) {
                state = UserDetailState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
            }
        }
    }

    private fun isValidRequest(): Boolean {
        nameError = user?.name.isNullOrEmpty()
        phoneError = user?.phone.isNullOrEmpty() || !TextHelper.isValidPhoneNumber(user?.phone)
        dateOfBirthError =
            user?.dateOfBirth.isNullOrEmpty() || !TextHelper.isValidDate(user?.dateOfBirth)
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

    fun updateLocation(locationResult: String?) {
        if (locationResult == null || user == null) return
        val location = LocationData.fromJson(locationResult)
        if (location.latitude == null || location.longitude == null) return
        if (location.latitude == Constants.DEFAULT_LATITUDE && location.longitude == Constants.DEFAULT_LONGITUDE) return
        viewModelScope.launch {
            user?.latitude = location.latitude
            user?.longitude = location.longitude
            user?.address = location.displayName
            kotlinx.coroutines.delay(1000)
            address = location.displayName.toString()
            locationData = LocationData(
                latitude = location.latitude,
                longitude = location.longitude,
                displayName = location.displayName
            )
        }
    }

    fun updateImage(imagePath: String?) {
        if (imagePath == null || user == null) return
        imageUrl = Constants.BASE_USER_URL + imagePath
        user?.imagePath = imagePath
        user?.imageUrl = imageUrl
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
        }
    }
}