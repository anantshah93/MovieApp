package iostudio.`in`.topmovies.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import iostudio.`in`.topmovies.R
import iostudio.`in`.topmovies.adapter.diffutil.PopularMovieDiffUtilCallback
import iostudio.`in`.topmovies.model.Movie
import javax.inject.Inject

class PopularMoviesAdapter @Inject constructor(
    private val callBack: (position: Int, movie: Movie?) -> Unit
) : RecyclerView.Adapter<PopularMoviesAdapter.ViewHolder>() {

    private var moviesList: List<Movie> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_popular_movie, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = moviesList[position]
        holder.picasso
            .load(movie.getFullPosterPath())
            .fit()
            .noFade()
            .placeholder(com.google.android.material.R.color.material_grey_100)
            .into(holder.imageViewPoster)

        holder.imageViewPoster.setOnClickListener { callBack.invoke(position, movie) }

    }

    override fun getItemCount(): Int = moviesList.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<Movie>) {
        val diffCallback = PopularMovieDiffUtilCallback(moviesList, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        moviesList = list
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val picasso: Picasso = Picasso.get()
        val imageViewPoster: ImageView = itemView.findViewById(R.id.imageViewPoster)
    }
}