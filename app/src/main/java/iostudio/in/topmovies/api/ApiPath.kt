package iostudio.`in`.topmovies.api

object ApiPath {
    private const val KEY_BASE_MOVIE = "3/movie"
    const val POPULAR_MOVIE = "$KEY_BASE_MOVIE/popular"
    const val MOVIE_DETAILS = "$KEY_BASE_MOVIE/{movie_id}"
}