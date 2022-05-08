package iostudio.`in`.topmovies.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import iostudio.`in`.topmovies.BuildConfig
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieData(
    @SerializedName("status_code") override val statusCode: Int? = null,
    @SerializedName("status_message") override val statusMessage: String? = null,
    @SerializedName("success") override val success: Boolean? = null,
    @SerializedName("page") val pageNumber: Int? = null,
    @SerializedName("results") var movies: List<Movie>? = null,
    @SerializedName("total_pages") val totalPagesCount: Int? = null,
    @SerializedName("total_results") val totalResultsCount: Int? = null,
) : CommonResponse(), Parcelable

@Parcelize
data class Movie(
    @SerializedName("status_code") override val statusCode: Int? = null,
    @SerializedName("status_message") override val statusMessage: String? = null,
    @SerializedName("success") override val success: Boolean? = null,
    @SerializedName("id") val id: String?,
    @SerializedName("adult") val isAdultMovie: Boolean? = null,
    @SerializedName("backdrop_path") val backDropPath: String? = null,
    @SerializedName("poster_path") val posterPath: String? = null,
    @SerializedName("original_language") val originalLanguage: String? = null,
    @SerializedName("original_title") val originalTitle: String? = null,
    @SerializedName("overview") val overview: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("homepage") val homePage: String? = null,
    @SerializedName("imdb_id") val imdbId: String? = null,
    @SerializedName("video") val video: Boolean? = null,
    @SerializedName("runtime") val runtime: String? = null,
    @SerializedName("release_date") val releaseDate: String? = null,
    @SerializedName("status") val status: String? = null,
    @SerializedName("tagline") val tagline: String? = null,
    @SerializedName("vote_average") val voteAverage: String? = null,
    @SerializedName("vote_count") val voteCount: String? = null,
) : CommonResponse(), Parcelable {

    fun getFullBackdropPath() = if (backDropPath.isNullOrBlank()) null else BuildConfig.ORIGINAL_IMAGE_URL + backDropPath

    fun getFullPosterPath() = if (posterPath.isNullOrBlank()) null else BuildConfig.ORIGINAL_IMAGE_URL + posterPath
}