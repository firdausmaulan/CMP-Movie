package fd.cmp.movie.data.repository

import fd.cmp.movie.data.model.User
import fd.cmp.movie.data.remote.api.core.ApiResponse
import fd.cmp.movie.data.remote.request.UserLoginRequest
import fd.cmp.movie.data.remote.response.UserLoginResponse

interface UserRepository {
    suspend fun login(request: UserLoginRequest): ApiResponse<UserLoginResponse>
    suspend fun insertUser(user: User?)
    suspend fun getUserByEmail(email: String): Result<User>
    suspend fun editUser(user: User?)
    fun saveEmail(email: String?)
    fun getEmail(): String
    fun saveToken(token : String?)
    fun getToken() : String
    fun logout()
}