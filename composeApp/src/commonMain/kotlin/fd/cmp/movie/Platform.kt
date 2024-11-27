package fd.cmp.movie

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform