package iostudio.`in`.topmovies.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import iostudio.`in`.topmovies.api.ApiParams.getPopularMoviesParam
import iostudio.`in`.topmovies.model.Movie
import iostudio.`in`.topmovies.model.MovieData
import iostudio.`in`.topmovies.repositories.IPopularMoviesRepository
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
    private val repository: IPopularMoviesRepository
) : BaseViewModel() {
    private val pageNumber = 1
    private val popularMovieListLiveData = MutableLiveData<List<Movie>?>()

    fun getPopularMoviesList() = popularMovieListLiveData

    init {
        loadPopularMovies()
    }

    private fun loadPopularMovies() {
        viewModelScope.launch {
            try {
                showLoading()
                val movieDataResponse: Response<MovieData> = repository.getPopularMoviesList(getPopularMoviesParam(pageNumber))
                if (movieDataResponse.isSuccessful) {
                    val movies: List<Movie> = movieDataResponse.body()?.movies.orEmpty()
                    popularMovieListLiveData.postValue(movies.take(10))
                } else {
                    showError("${movieDataResponse.message()} code:${movieDataResponse.code()}")
                }
                hideLoading()
            } catch (e: Exception) {
                onError(e)
            }
        }
    }
}