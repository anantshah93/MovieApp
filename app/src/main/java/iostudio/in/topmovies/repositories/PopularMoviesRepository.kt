package iostudio.`in`.topmovies.repositories

import iostudio.`in`.topmovies.api.IApiService
import javax.inject.Inject

class PopularMoviesRepository @Inject constructor(
    private val apiService: IApiService
) : IPopularMoviesRepository {

    override suspend fun getPopularMoviesList(hashMap: HashMap<String, String>) = apiService.getPopularMovies(hashMap)
}