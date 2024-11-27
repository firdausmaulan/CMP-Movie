package fd.cmp.movie.data.remote.api.core

import fd.cmp.movie.helper.Constants
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.setBody
import io.ktor.client.statement.request
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType

open class AppHttpClient(val httpClient: HttpClient) {

    companion object {
        const val BASE_URL = Constants.BASE_URL
        const val TOKEN = Constants.APP_KEY
    }

    /**
     * Generic GET request handler
     * @param endpoint The API endpoint
     * @param parameters Optional query parameters
     * @return ApiResponse<T> containing the result or error
     */
    suspend inline fun <reified T> get(
        endpoint: String,
        parameters: Map<String, String> = emptyMap()
    ): ApiResponse<T> {
        return try {
            val response = httpClient.get(BASE_URL + endpoint) {
                parameters.forEach { (key, value) ->
                    parameter(key, value)
                }
                headers {
                    bearerAuth(TOKEN)
                    contentType(ContentType.Application.Json)
                }
            }

            println("API Full URL: ${response.request.url}")
            println("API Response Body: ${response.body<T>()}")
            println("API Response: $response")

            when (response.status) {
                HttpStatusCode.OK -> ApiResponse.Success(response.body())
                else -> ApiResponse.Error(
                    code = response.status.value,
                    message = response.status.description
                )
            }
        } catch (e: Exception) {
            println("AppHttpClient get error : " + e.message)
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
        endpoint: String,
        body: R,
        parameters: Map<String, String> = emptyMap()
    ): ApiResponse<T> {
        return try {
            val response = httpClient.get(BASE_URL + endpoint) {
                parameters.forEach { (key, value) ->
                    parameter(key, value)
                }
                headers {
                    bearerAuth(TOKEN)
                    contentType(ContentType.Application.Json)
                }
                setBody(body)
            }

            println("API Full URL: ${response.request.url}")
            println("API Response Body: ${response.body<T>()}")
            println("API Response: $response")

            when (response.status) {
                HttpStatusCode.OK, HttpStatusCode.Created -> ApiResponse.Success(response.body())
                else -> ApiResponse.Error(
                    code = response.status.value,
                    message = response.status.description
                )
            }
        } catch (e: Exception) {
            println("AppHttpClient get error : " + e.message)
            ApiResponse.Error(
                code = 500,
                message = e.message ?: "Unknown error occurred"
            )
        }
    }
}