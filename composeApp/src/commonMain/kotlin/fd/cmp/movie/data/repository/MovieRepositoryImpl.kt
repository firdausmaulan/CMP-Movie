package fd.cmp.movie.data.repository

import fd.cmp.movie.data.model.Movie
import fd.cmp.movie.data.remote.api.core.ApiResponse
import fd.cmp.movie.data.remote.api.service.MovieApiService
import fd.cmp.movie.data.remote.request.MovieRequest
import fd.cmp.movie.data.remote.response.MoviesResponse

class MovieRepositoryImpl(private val movieApiService: MovieApiService) : MovieRepository {

    override suspend fun search(request: MovieRequest): ApiResponse<MoviesResponse> {
        val movies = movieApiService.search(request)
        val genres = movieApiService.genres()
        if (movies is ApiResponse.Success && genres is ApiResponse.Success) {
            val moviesData = movies.data
            val genresData = genres.data
            val moviesWithGenreNames = moviesData.results?.map { movie ->
                val genreIds = movie.genreIds
                val genreNames = genreIds?.mapNotNull { genreId ->
                    genresData.genres?.find { it.id == genreId }?.name
                }
                val formattedGenreNames = genreNames?.joinToString(", ")
                movie.copy(genreNames = genreNames, formattedGenreNames = formattedGenreNames)
            }
            return ApiResponse.Success(moviesData.copy(results = moviesWithGenreNames))
        }
        return ApiResponse.Error(500, "Unknown error occurred")
    }

    override suspend fun detail(id: Int?): ApiResponse<Movie> {
        val movie = movieApiService.detail(id)
        val genres = movieApiService.genres()
        if (movie is ApiResponse.Success && genres is ApiResponse.Success) {
            val movieData = movie.data
            val genresData = genres.data
            val genreIds = movieData.genreIds
            val genreNames = genreIds?.mapNotNull { genreId ->
                genresData.genres?.find { it.id == genreId }?.name
            }
            val formattedGenreNames = genreNames?.joinToString(", ")
            return ApiResponse.Success(
                movieData.copy(
                    genreNames = genreNames,
                    formattedGenreNames = formattedGenreNames
                )
            )
        }
        return ApiResponse.Error(500, "Unknown error occurred")
    }
}