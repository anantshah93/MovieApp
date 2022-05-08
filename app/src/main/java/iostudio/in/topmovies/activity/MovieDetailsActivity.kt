package iostudio.`in`.topmovies.activity

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import iostudio.`in`.topmovies.R
import iostudio.`in`.topmovies.extension.hide
import iostudio.`in`.topmovies.extension.show
import iostudio.`in`.topmovies.model.Movie
import iostudio.`in`.topmovies.viewmodel.BaseViewModel
import iostudio.`in`.topmovies.viewmodel.MoviesDetailsViewModel
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation

const val KEY_MOVIE_OBJECT: String = "Movie"
const val CORNER_RADIUS: Int = 20

@AndroidEntryPoint
class MovieDetailsActivity : BaseActivity<BaseViewModel>() {

    override val viewModel: MoviesDetailsViewModel by viewModels()


    private val imageViewBack: ImageView by lazy { findViewById(R.id.imageViewBack) }
    private val imageViewPoster: ImageView by lazy { findViewById(R.id.imageViewPoster) }
    private val imageViewCoverPoster: ImageView by lazy { findViewById(R.id.imageViewCoverPoster) }
    private val textViewTitle: TextView by lazy { findViewById(R.id.textViewTitle) }
    private val textViewTagLine: TextView by lazy { findViewById(R.id.textViewTagLine) }
    private val textViewDuration: TextView by lazy { findViewById(R.id.textViewDuration) }
    private val textViewIMDB: TextView by lazy { findViewById(R.id.textViewIMDB) }
    private val textViewRelease: TextView by lazy { findViewById(R.id.textViewRelease) }
    private val textViewOverview: TextView by lazy { findViewById(R.id.textViewOverview) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        val bundle = intent.extras
        if (bundle?.containsKey(KEY_MOVIE_OBJECT) == true) {
            try {
                val movie: Movie = bundle.getParcelable(KEY_MOVIE_OBJECT)!!
                movie.id?.let { viewModel.getMovieDetails(it) }
                setData(movie)
            } catch (e: Exception) {
                e.message?.let { showToast(it) }
                onBackPressed()
            }
        } else {
            showToast(getString(R.string.error_movie_object))
            onBackPressed()
        }

        imageViewBack.setOnClickListener { onBackPressed() }

        initObserver()
    }

    private fun setData(movie: Movie) {
        Picasso.get().load(movie.getFullBackdropPath())
            .fit()
            .noFade()
            .placeholder(R.color.darker_blue)
            .into(imageViewCoverPoster)

        Picasso.get().load(movie.getFullPosterPath())
            .fit()
            .noFade()
            .transform(RoundedCornersTransformation(CORNER_RADIUS, 0))
            .placeholder(R.color.darker_blue)
            .into(imageViewPoster)

        textViewTitle.text = movie.title

        movie.tagline?.let {
            textViewTagLine.text = it
            textViewTagLine.show()
        } ?: run { textViewTagLine.hide() }


        textViewDuration.text = movie.runtime?.let { getString(R.string.movie_duration, it) } ?: "-"
        textViewIMDB.text = movie.voteAverage?.let { getString(R.string.vote_average, it) } ?: "-"
        textViewRelease.text = movie.releaseDate?.let { getString(R.string.release_date, getFormattedDate(it)) } ?: "-"
        textViewOverview.text = movie.overview ?: "-"
    }

    private fun initObserver() {
        viewModel.getMovieDetailsLiveData().observe(this) { it?.let { setData(it) } }
    }

}