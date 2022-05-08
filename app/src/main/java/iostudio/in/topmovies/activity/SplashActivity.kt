package iostudio.`in`.topmovies.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import iostudio.`in`.topmovies.R
import iostudio.`in`.topmovies.viewmodel.SplashViewModel

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        initObserver()
    }

    private fun initObserver() {
        viewModel.getSplashLiveData().observe(this) {
            if (it.showPopularMovieScreen) {
                val intent = Intent(this, PopularMoviesActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}