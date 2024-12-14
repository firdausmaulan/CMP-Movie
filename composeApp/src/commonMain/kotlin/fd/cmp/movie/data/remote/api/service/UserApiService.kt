package fd.cmp.movie.data.remote.api.service

import fd.cmp.movie.data.remote.api.core.ApiResponse
import fd.cmp.movie.data.remote.api.core.AppHttpClient
import fd.cmp.movie.data.remote.request.UserLoginRequest
import fd.cmp.movie.data.remote.response.UserLoginResponse
import fd.cmp.movie.helper.Constants

class UserApiService(private val appHttpClient: AppHttpClient) {

    suspend fun login(request: UserLoginRequest): ApiResponse<UserLoginResponse> {

        return appHttpClient.post(
            baseUrl = Constants.BASE_USER_URL,
            endpoint = "login.php",
            body = request
        )
    }

}