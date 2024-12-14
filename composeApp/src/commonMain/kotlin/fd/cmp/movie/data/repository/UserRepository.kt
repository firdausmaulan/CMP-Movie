package fd.cmp.movie.data.repository

import fd.cmp.movie.data.remote.api.core.ApiResponse
import fd.cmp.movie.data.remote.request.UserLoginRequest
import fd.cmp.movie.data.remote.response.UserLoginResponse

interface UserRepository {
    suspend fun login(request: UserLoginRequest): ApiResponse<UserLoginResponse>
    fun saveToken(token : String?)
    fun getToken() : String
}