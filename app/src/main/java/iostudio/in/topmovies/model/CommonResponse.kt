package iostudio.`in`.topmovies.model

import com.google.gson.annotations.SerializedName

sealed class CommonResponse {
     abstract val statusCode: Int?
     abstract val statusMessage: String?
     abstract val success: Boolean?
}
