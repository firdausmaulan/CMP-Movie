package fd.cmp.movie.data.repository

import fd.cmp.movie.data.local.AppSettings
import fd.cmp.movie.data.remote.api.core.ApiResponse
import fd.cmp.movie.data.remote.api.service.UserApiService
import fd.cmp.movie.data.remote.request.UserLoginRequest
import fd.cmp.movie.data.remote.response.UserLoginResponse

class UserRepositoryImpl(
    private val apiService: UserApiService,
    private val appSettings: AppSettings
) : UserRepository {

    override suspend fun login(request: UserLoginRequest): ApiResponse<UserLoginResponse> {
        return apiService.login(request)
    }

    override fun saveToken(token: String?) {
        appSettings.saveToken(token)
    }

    override fun getToken(): String {
        return appSettings.getToken()
    }
}