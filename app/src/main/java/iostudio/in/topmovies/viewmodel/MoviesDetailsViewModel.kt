package iostudio.`in`.topmovies.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import iostudio.`in`.topmovies.model.Movie
import iostudio.`in`.topmovies.repositories.IMovieDetailRepository
import iostudio.`in`.topmovies.repositories.MovieDetailRepository
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class MoviesDetailsViewModel @Inject constructor(
    private val repository: IMovieDetailRepository
) : BaseViewModel() {
    private val movieDetailsLiveData = MutableLiveData<Movie>()

    fun getMovieDetailsLiveData() = movieDetailsLiveData

     fun getMovieDetails(movieId: String) {
        viewModelScope.launch {
            try {
                showLoading()
                val movieDataResponse: Response<Movie> = repository.getMovieDetails(movieId)
                if (movieDataResponse.isSuccessful) {
                    val movie: Movie? = movieDataResponse.body()
                    movieDetailsLiveData.postValue(movie)
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