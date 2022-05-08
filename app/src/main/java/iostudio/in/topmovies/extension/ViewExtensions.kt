package iostudio.`in`.topmovies.extension

import android.view.View


/** makes visible a view. */
fun View.show() {
    visibility = View.VISIBLE
}

/** makes gone a view. */
fun View.hide() {
    visibility = View.GONE
}
