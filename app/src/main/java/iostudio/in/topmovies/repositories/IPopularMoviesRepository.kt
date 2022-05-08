package iostudio.`in`.topmovies.repositories

import iostudio.`in`.topmovies.model.MovieData
import retrofit2.Response

interface IPopularMoviesRepository {
    suspend fun getPopularMoviesList(hashMap: HashMap<String, String> = HashMap()): Response<MovieData>
}