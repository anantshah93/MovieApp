package iostudio.`in`.topmovies.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import iostudio.`in`.topmovies.BuildConfig

@HiltAndroidApp
class MoviesApp : Application()

fun isDebugMode() = BuildConfig.BUILD_TYPE != "release"
