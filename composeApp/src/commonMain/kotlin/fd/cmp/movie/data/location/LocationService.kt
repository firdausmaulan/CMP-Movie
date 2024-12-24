package fd.cmp.movie.data.location

expect class LocationService {
    suspend fun getLocationName(latitude: Double, longitude: Double): LocationDetailResult
}

expect fun provideLocationService(): LocationService