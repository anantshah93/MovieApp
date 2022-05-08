package iostudio.`in`.topmovies.model

import iostudio.`in`.topmovies.BuildConfig
import org.junit.Assert.assertEquals
import org.junit.Test

class MovieTest {

    @Test
    fun getFullBackDropPath_backdropPathIsNull() {
        assertEquals(null, Movie(id = "1").getFullBackdropPath())
    }

    @Test
    fun getFullBackDropPath_backdropPathIsBlank() {
        assertEquals(null, Movie(id = "1", backDropPath = "   ").getFullBackdropPath())
    }

    @Test
    fun getFullBackDropPath_backdropPathIsNotNullAndNotBlank() {
        val path = "0000"
        assertEquals(BuildConfig.ORIGINAL_IMAGE_URL + path, Movie(id = "1", backDropPath = path).getFullBackdropPath())
    }

    @Test
    fun getPosterPath_posterPathIsNull() {
        assertEquals(null, Movie(id = "1").getFullPosterPath())
    }

    @Test
    fun getPosterPath_posterPathIsBlank() {
        assertEquals(null, Movie(id = "1", posterPath = "   ").getFullPosterPath())
    }

    @Test
    fun getPosterPath_posterPathIsNotNullAndNotBlank() {
        val path = "1111"
        assertEquals(BuildConfig.ORIGINAL_IMAGE_URL + path, Movie(id = "1", posterPath = path).getFullPosterPath())
    }
}