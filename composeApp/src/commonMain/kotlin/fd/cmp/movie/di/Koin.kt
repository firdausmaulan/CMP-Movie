package fd.cmp.movie.di

import fd.cmp.movie.data.local.db.service.UserDbService
import fd.cmp.movie.data.local.keyval.AppSettings
import fd.cmp.movie.data.local.keyval.providePlatformSettings
import fd.cmp.movie.data.location.provideLocationService
import fd.cmp.movie.data.remote.api.core.AppHttpClient
import fd.cmp.movie.data.remote.api.service.MovieApiService
import fd.cmp.movie.data.remote.api.service.UserApiService
import fd.cmp.movie.data.repository.MovieRepository
import fd.cmp.movie.data.repository.MovieRepositoryImpl
import fd.cmp.movie.data.repository.UserRepository
import fd.cmp.movie.data.repository.UserRepositoryImpl
import fd.cmp.movie.screen.detail.MovieDetailViewModel
import fd.cmp.movie.screen.list.MovieListViewModel
import fd.cmp.movie.screen.location.LocationViewModel
import fd.cmp.movie.screen.login.UserLoginViewModel
import fd.cmp.movie.screen.user.UserViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import sp.bvantur.inspektify.ktor.InspektifyKtor

val dataModule = module {
    single {
        val json = Json { ignoreUnknownKeys = true }
        HttpClient {
            install(ContentNegotiation) {
                json(json, contentType = ContentType.Any)
            }
            install(Logging) {
                level = LogLevel.ALL
            }
            install(InspektifyKtor) {
                logLevel = sp.bvantur.inspektify.ktor.LogLevel.All
                shortcutEnabled = true
            }
        }
    }
    single { providePlatformSettings() }
    single { AppSettings(get()) }
    single { AppHttpClient(get(), get()) }

    single { MovieApiService(get()) }
    single<MovieRepository> { MovieRepositoryImpl(get()) }

    single { UserApiService(get()) }
    single { UserDbService() }
    single<UserRepository> { UserRepositoryImpl(get(), get(), get()) }

    single { provideLocationService() }
}

val viewModelModule = module {
    factoryOf(::MovieListViewModel)
    factoryOf(::MovieDetailViewModel)
    factoryOf(::UserLoginViewModel)
    factoryOf(::UserViewModel)
    factoryOf(::LocationViewModel)
}

fun initKoin() {
    startKoin {
        modules(
            dataModule,
            viewModelModule,
        )
    }
}
