package iostudio.`in`.topmovies

import iostudio.`in`.topmovies.model.Movie

/** MockUtil provides mock entities and  models data  */
class MockTestUtils {
    companion object {

        /** mocks a [Movie] entity. */
        private fun mockMovie(id:String) = Movie(
            statusCode = 200,
            id = id,
            isAdultMovie = false,
            backDropPath = "",
            posterPath = "",
            originalLanguage = "",
            originalTitle = "",
            overview = "",
            title = "",
            homePage = "",
            imdbId = "",
            video = false,
            runtime = "",
            releaseDate = "",
            status = "released",
            tagline = "",
            voteAverage = "7.8",
            voteCount = "",

        )


        /** mocks a list of [Movie] entity. */
        fun mockMovieList(): List<Movie> {
            val movies = ArrayList<Movie>()
            movies.add(mockMovie("1"))
            movies.add(mockMovie("2"))
            movies.add(mockMovie("3"))
            return movies
        }
    }
}