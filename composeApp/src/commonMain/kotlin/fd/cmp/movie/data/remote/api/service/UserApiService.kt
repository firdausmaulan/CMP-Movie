package fd.cmp.movie.data.remote.api.service

import fd.cmp.movie.data.remote.api.core.ApiResponse
import fd.cmp.movie.data.remote.api.core.AppHttpClient
import fd.cmp.movie.data.remote.request.LoginRequest
import fd.cmp.movie.data.remote.response.MoviesResponse

class AuthApiService(private val appHttpClient: AppHttpClient) {

    suspend fun search(request: LoginRequest): ApiResponse<MoviesResponse> {
        val parameters = mapOf(
            "username" to request.username,
            "password" to request.password
        )
        return appHttpClient.get(endpoint = "search/movie", parameters = parameters)
    }

}