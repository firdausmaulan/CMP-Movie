package fd.cmp.movie.data.remote.api.core

import fd.cmp.movie.data.local.AppSettings
import fd.cmp.movie.helper.Constants
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType

open class AppHttpClient(val httpClient: HttpClient, val appSettings: AppSettings) {

    companion object {
        const val BASE_URL = Constants.BASE_URL
    }

    /**
     * Generic GET request handler
     * @param endpoint The API endpoint
     * @param parameters Optional query parameters
     * @return ApiResponse<T> containing the result or error
     */
    suspend inline fun <reified T> get(
        baseUrl: String = BASE_URL,
        endpoint: String,
        parameters: Map<String, String> = emptyMap()
    ): ApiResponse<T> {
        return try {
            val response = httpClient.get(baseUrl + endpoint) {
                parameters.forEach { (key, value) ->
                    parameter(key, value)
                }
                headers {
                    bearerAuth(appSettings.getToken())
                    contentType(ContentType.Application.Json)
                }
            }

            when (response.status) {
                HttpStatusCode.OK -> ApiResponse.Success(response.body())
                else -> ApiResponse.Error(
                    code = response.status.value,
                    message = response.status.description
                )
            }
        } catch (e: Exception) {
            ApiResponse.Error(
                code = 500,
                message = e.message ?: "Unknown error occurred"
            )
        }
    }

    /**
     * Generic POST request handler
     * @param endpoint The API endpoint
     * @param body The request body
     * @param parameters Optional query parameters
     * @return ApiResponse<T> containing the result or error
     */
    suspend inline fun <reified T, reified R> post(
        baseUrl: String = BASE_URL,
        endpoint: String,
        body: R,
        parameters: Map<String, String> = emptyMap()
    ): ApiResponse<T> {
        return try {
            val response = httpClient.post(baseUrl + endpoint) {
                parameters.forEach { (key, value) ->
                    parameter(key, value)
                }
                headers {
                    bearerAuth(appSettings.getToken())
                    contentType(ContentType.Application.Json)
                }
                setBody(body)
            }

            when (response.status) {
                HttpStatusCode.OK, HttpStatusCode.Created -> ApiResponse.Success(response.body())
                else -> ApiResponse.Error(
                    code = response.status.value,
                    message = response.status.description
                )
            }
        } catch (e: Exception) {
            ApiResponse.Error(
                code = 500,
                message = e.message ?: "Unknown error occurred"
            )
        }
    }
}