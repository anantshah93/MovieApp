package iostudio.`in`.topmovies.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import iostudio.`in`.topmovies.BuildConfig
import iostudio.`in`.topmovies.MockTestUtils.Companion.mockMovieList
import iostudio.`in`.topmovies.api.ApiParams
import iostudio.`in`.topmovies.mock
import iostudio.`in`.topmovies.model.Movie
import iostudio.`in`.topmovies.model.MovieData
import iostudio.`in`.topmovies.repositories.IPopularMoviesRepository
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
import org.mockito.Mockito.atLeastOnce
import org.mockito.Mockito.verify
import retrofit2.Response

@ExperimentalCoroutinesApi
class PopularMoviesViewModelTest {

    private lateinit var viewModel: PopularMoviesViewModel
    private val popularMoviesRepository = mock<IPopularMoviesRepository>()


    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        this.viewModel = PopularMoviesViewModel(popularMoviesRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testGetPopularMoviesList(): Unit = runBlocking {
        launch(Dispatchers.Main) {

            val mockResponse = MovieData(statusCode = 200, pageNumber = 1, movies = mockMovieList(), totalResultsCount = 50, totalPagesCount = 5)
            val mockResponseRetrofit: Response<MovieData> =
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
            val observer = mock<Observer<List<Movie>?>>()
            viewModel.getPopularMoviesList().observeForever(observer)

            val data = popularMoviesRepository.getPopularMoviesList(ApiParams.getPopularMoviesParam(1))
            Mockito.`when`(data).thenReturn(mockResponseRetrofit)

            // when
            viewModel.getPopularMoviesList().postValue(mockResponse.movies)

            // then
            Assert.assertEquals(3, viewModel.getPopularMoviesList().value?.size)
            Assert.assertEquals("1", viewModel.getPopularMoviesList().value?.getOrNull(0)?.id)
            Assert.assertEquals("2", viewModel.getPopularMoviesList().value?.getOrNull(1)?.id)
            Assert.assertEquals("3", viewModel.getPopularMoviesList().value?.getOrNull(2)?.id)

            verify(observer).onChanged(mockResponse.movies)

            viewModel.getPopularMoviesList().removeObserver(observer)
        }
    }


}