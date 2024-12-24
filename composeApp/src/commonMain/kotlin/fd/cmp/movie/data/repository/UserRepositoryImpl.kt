package fd.cmp.movie.data.repository

import fd.cmp.movie.data.local.keyval.AppSettings
import fd.cmp.movie.data.local.db.service.UserDbService
import fd.cmp.movie.data.model.User
import fd.cmp.movie.data.remote.api.core.ApiResponse
import fd.cmp.movie.data.remote.api.service.UserApiService
import fd.cmp.movie.data.remote.request.UserLoginRequest
import fd.cmp.movie.data.remote.response.PhotoResponse
import fd.cmp.movie.data.remote.response.UserLoginResponse

class UserRepositoryImpl(
    private val apiService: UserApiService,
    private val userDbService: UserDbService,
    private val appSettings: AppSettings
) : UserRepository {

    override suspend fun login(request: UserLoginRequest): ApiResponse<UserLoginResponse> {
        return apiService.login(request)
    }

    override suspend fun uploadPhoto(photo: ByteArray?): ApiResponse<PhotoResponse> {
        return apiService.uploadPhoto(photo)
    }

    override suspend fun insertUser(user: User?) {
        userDbService.insertUser(user)
    }

    override suspend fun getUserByEmail(email: String): Result<User> {
        return userDbService.selectUserByEmail(email)
    }

    override suspend fun editUser(user: User?) {
        return userDbService.updateUser(user)
    }

    override fun saveEmail(email: String?) {
        appSettings.saveEmail(email)
    }

    override fun getEmail(): String {
        return appSettings.getEmail()
    }

    override fun saveToken(token: String?) {
        appSettings.saveToken(token)
    }

    override fun getToken(): String {
        return appSettings.getToken()
    }

    override fun logout() {
        appSettings.clear()
    }
}