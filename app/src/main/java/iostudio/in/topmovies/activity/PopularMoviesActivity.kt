package iostudio.`in`.topmovies.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import iostudio.`in`.topmovies.R
import iostudio.`in`.topmovies.adapter.PopularMoviesAdapter
import iostudio.`in`.topmovies.viewmodel.BaseViewModel
import iostudio.`in`.topmovies.viewmodel.PopularMoviesViewModel

@AndroidEntryPoint
class PopularMoviesActivity : BaseActivity<BaseViewModel>() {

    override val viewModel: PopularMoviesViewModel by viewModels()

    private val recyclerViewPopularMovies: RecyclerView by lazy { findViewById(R.id.recyclerViewPopularMovies) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popular_movies)

        initView()
        initObserver()
    }

    private fun initView() {
        recyclerViewPopularMovies.let {
            it.layoutManager = LinearLayoutManager(this)
            it.setHasFixedSize(false)
        }
    }

    private fun initObserver() {
        viewModel.getPopularMoviesList().observe(this) {
            it?.let {
                recyclerViewPopularMovies.adapter = PopularMoviesAdapter { _, movie ->
                    run {
                        val intent = Intent(this, MovieDetailsActivity::class.java)
                        intent.putExtra(KEY_MOVIE_OBJECT, movie)
                        startActivity(intent)
                    }
                }.also { movieList ->
                    movieList.submitList(it)
                }
            }
        }
    }
}