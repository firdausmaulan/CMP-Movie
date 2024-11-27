package fd.cmp.movie.data.remote.request

import fd.cmp.movie.helper.Constants

data class MovieRequest(
    var query: String = Constants.DEFAULT_QUERY,
    var page: Int = 1,
    var language: String = "en-US"
)
