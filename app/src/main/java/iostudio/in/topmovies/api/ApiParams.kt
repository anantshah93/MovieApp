package iostudio.`in`.topmovies.api

object ApiParams {

    const val KEY_PAGE = "page"
    const val KEY_SORT_BY = "sort_by"
    const val KEY_LANGUAGE = "language"

    const val VALUE_POPULARITY_DESC = "popularity.desc"
    const val VALUE_LANGUAGE_ENGLISH = "en-US"

    fun getPopularMoviesParam(pageNumber: Int?): HashMap<String, String> {
        val hashMap = HashMap<String, String>()
        hashMap[KEY_PAGE] = (pageNumber).toString()
        hashMap[KEY_LANGUAGE] = VALUE_LANGUAGE_ENGLISH
        hashMap[KEY_SORT_BY] = VALUE_POPULARITY_DESC
        return hashMap
    }
}
