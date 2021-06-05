package br.com.altechno.sbmovies.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import br.com.altechno.sbmovies.R
import br.com.altechno.sbmovies.model.MovieSearch
import com.bumptech.glide.Glide

class MoviesAdapter(
    private val movies: List<MovieSearch>,
    val listener: (MovieSearch) -> Unit
) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.img_movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_movies_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]

        Glide.with(holder.itemView)
            .load(movie.poster)
            .placeholder(R.drawable.ic_launcher_foreground)
            .centerCrop()
            .into(holder.image)

        holder.itemView.setOnClickListener {
            listener(movie)
        }
    }
}