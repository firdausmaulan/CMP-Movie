package fd.cmp.movie.screen.login

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fd.myblog.data.local.preference.AppPreference
import com.fd.myblog.data.remote.api.core.Result
import com.fd.myblog.data.remote.api.service.UserApiService
import com.fd.myblog.data.remote.request.UserLoginRequest
import kotlinx.coroutines.launch

class UserLoginViewModel(
    private val userApiService: UserApiService,
    private val preference: AppPreference
) : ViewModel() {

    var state by mutableStateOf<UserLoginState>(UserLoginState.Idle)

    var emailError by mutableStateOf(false)

    var passwordError by mutableStateOf(false)

    fun isValidEmail(email: String?): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email.toString()).matches()
    }

    fun isValidPassword(password: String?): Boolean {
        return password?.isNotEmpty() == true && password.length >= 8
    }

    private fun isValidRequest(request: UserLoginRequest): Boolean {
        return request.email != null &&
                request.password != null &&
                isValidEmail(request.email) &&
                request.password.length >= 8
    }

    fun login(request: UserLoginRequest) {
        if (!isValidRequest(request)) return
        viewModelScope.launch {
            state = UserLoginState.Loading
            val result = userApiService.login(request)
            when (result) {
                is Result.Success -> {
                    state = UserLoginState.Success(result.data.user)
                    preference.saveUser(result.data.user)
                }

                is Result.Error -> {
                    state = UserLoginState.Error("Failed to login: ${result.error}")
                }
            }
        }
    }
}
