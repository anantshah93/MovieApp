package iostudio.`in`.topmovies.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import iostudio.`in`.topmovies.BuildConfig
import iostudio.`in`.topmovies.MockTestUtils
import iostudio.`in`.topmovies.api.ApiParams
import iostudio.`in`.topmovies.mock
import iostudio.`in`.topmovies.model.Movie
import iostudio.`in`.topmovies.model.MovieData
import iostudio.`in`.topmovies.repositories.IMovieDetailRepository
import iostudio.`in`.topmovies.repositories.IPopularMoviesRepository
import iostudio.`in`.topmovies.utils.toDate
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import okhttp3.Protocol
import okhttp3.Request
import org.junit.*
import org.mockito.Mockito
import retrofit2.Response

@ExperimentalCoroutinesApi
class MoviesDetailsViewModelTest{

    private lateinit var viewModel: MoviesDetailsViewModel
    private val movieRepository = mock<IMovieDetailRepository>()
    private val emptyString = ""
    private val blankString = "       "
    private val sourceDateFormat = "yyyy-MM-dd"

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        this.viewModel = MoviesDetailsViewModel(movieRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getMovieDetailsTest(): Unit = runBlocking {
        launch(Dispatchers.Main) {

            val mockResponse = MockTestUtils.mockMovieList()[0]
            val mockResponseRetrofit: Response<Movie> =
                Response.success(
                    mockResponse,
                    okhttp3.Response.Builder()
                        .code(200)
                        .message("Response.success()")
                        .protocol(Protocol.HTTP_1_1)
                        .request(Request.Builder().url(BuildConfig.BASE_URL).build())
                        .receivedResponseAtMillis(1619053449513)
                        .sentRequestAtMillis(1619053443814)
                        .build()
                )
            val observer = mock<Observer<Movie>>()
            viewModel.getMovieDetailsLiveData().observeForever(observer)

            val data = movieRepository.getMovieDetails("414906")
            Mockito.`when`(data).thenReturn(mockResponseRetrofit)

            // when
            viewModel.getMovieDetailsLiveData().postValue(mockResponse)

            // then
            Assert.assertEquals("1", viewModel.getMovieDetailsLiveData().value?.id)
            Assert.assertEquals(null, viewModel.getMovieDetailsLiveData().value?.releaseDate?.toDate(sourceDateFormat))


            Mockito.verify(observer).onChanged(mockResponse)
            viewModel.getMovieDetailsLiveData().removeObserver(observer)
        }
    }


}