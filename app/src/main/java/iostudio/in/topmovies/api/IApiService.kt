package iostudio.`in`.topmovies.api

import iostudio.`in`.topmovies.model.Movie
import iostudio.`in`.topmovies.model.MovieData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface IApiService {
    @GET(ApiPath.POPULAR_MOVIE)
    suspend fun getPopularMovies(@QueryMap hashMap: HashMap<String, String> = HashMap()): Response<MovieData>

   /* @GET(ApiPath.MOVIE_DETAILS)
    suspend fun getMovieDetails(@QueryMap hashMap: HashMap<String, String> = HashMap()): Response<Movie>
*/
    @GET(ApiPath.MOVIE_DETAILS)
    suspend fun getMovieDetails(@Path("movie_id") movieId: String, @QueryMap hashMap: HashMap<String, String> = HashMap()): Response<Movie>
}