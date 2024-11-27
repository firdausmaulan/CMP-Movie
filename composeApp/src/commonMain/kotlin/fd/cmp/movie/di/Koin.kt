package fd.cmp.movie.di

import fd.cmp.movie.data.remote.api.core.AppHttpClient
import fd.cmp.movie.data.remote.api.service.MovieApiService
import fd.cmp.movie.data.repository.MovieRepository
import fd.cmp.movie.data.repository.MovieRepositoryImpl
import fd.cmp.movie.screen.detail.MovieDetailViewModel
import fd.cmp.movie.screen.list.MovieListViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val dataModule = module {
    single {
        val json = Json { ignoreUnknownKeys = true }
        HttpClient {
            install(ContentNegotiation) {
                json(json, contentType = ContentType.Any)
            }
        }
    }

    single { AppHttpClient(get()) }
    single { MovieApiService(get()) }
    single<MovieRepository> { MovieRepositoryImpl(get()) }
}

val viewModelModule = module {
    factoryOf(::MovieListViewModel)
    factoryOf(::MovieDetailViewModel)
}

fun initKoin() {
    startKoin {
        modules(
            dataModule,
            viewModelModule,
        )
    }
}
