package iostudio.`in`.topmovies.repositories

import iostudio.`in`.topmovies.api.IApiService
import iostudio.`in`.topmovies.model.Movie
import iostudio.`in`.topmovies.model.MovieData
import retrofit2.Response
import javax.inject.Inject


interface IMovieDetailRepository {
    suspend fun getMovieDetails(movieID: String, hashMap: HashMap<String, String> = HashMap()): Response<Movie>
}

class MovieDetailRepository @Inject constructor(
    private val apiService: IApiService
) : IMovieDetailRepository {

    override suspend fun getMovieDetails(movieID: String,hashMap: HashMap<String, String>) = apiService.getMovieDetails(movieID)
}