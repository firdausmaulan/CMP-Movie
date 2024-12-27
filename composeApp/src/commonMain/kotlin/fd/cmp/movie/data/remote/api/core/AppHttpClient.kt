package fd.cmp.movie.data.remote.api.core

import fd.cmp.movie.data.local.keyval.AppSettings
import fd.cmp.movie.helper.Constants
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject

open class AppHttpClient(val httpClient: HttpClient, val appSettings: AppSettings) {

    companion object {
        const val BASE_URL = Constants.BASE_URL
    }

    fun getErrorMessages(responseBody: String): String {
        try {
            val jsonObject = Json.parseToJsonElement(responseBody) as? JsonObject ?: return responseBody
            val message = jsonObject["message"]?.toString() ?: return responseBody
            return message
        } catch (e: Exception) {
            return responseBody
        }
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
                    message = getErrorMessages(response.bodyAsText())
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
                    message = getErrorMessages(response.bodyAsText())
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
     * Uploads an image as ByteArray to the server
     * @param endpoint The API endpoint
     * @param fileParameter The name of the file parameter
     * @param fileAsByteArray The file as ByteArray
     * @param fileType The type of the file
     * @param fileName The name of the file
     * @param parameters Optional query parameters
     * @return ApiResponse<T> containing the result or error
     */
    suspend inline fun <reified T> upload(
        baseUrl: String = BASE_URL,
        endpoint: String,
        fileParameter : String = "image",
        fileAsByteArray: ByteArray?,
        fileType : String = "image/jpeg",
        fileName : String = "image.jpg",
        parameters: Map<String, String> = emptyMap()
    ): ApiResponse<T> {
        if (fileAsByteArray == null) {
            return ApiResponse.Error(
                code = 400,
                message = "File is required"
            )
        }
        return try {
            val response = httpClient.post(baseUrl + endpoint) {
                parameters.forEach { (key, value) ->
                    parameter(key, value)
                }
                headers {
                    bearerAuth(appSettings.getToken())
                }

                setBody(MultiPartFormDataContent(
                    formData {
                        append(fileParameter, fileAsByteArray, Headers.build {
                            append(HttpHeaders.ContentType, fileType)
                            append(HttpHeaders.ContentDisposition, "filename=$fileName")
                        })
                    }
                ))
            }

            when (response.status) {
                HttpStatusCode.OK, HttpStatusCode.Created -> ApiResponse.Success(response.body())
                else -> ApiResponse.Error(
                    code = response.status.value,
                    message = getErrorMessages(response.bodyAsText())
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