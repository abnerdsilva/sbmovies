package br.com.altechno.sbmovies.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import br.com.altechno.sbmovies.R
import br.com.altechno.sbmovies.model.MovieSearch
import com.bumptech.glide.Glide

class MoviesGridAdapter(
    private val movies: List<MovieSearch>,
    private val context: Context,
    val listener: (MovieSearch) -> Unit
) : BaseAdapter() {

    private val layoutInflator = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return movies.size
    }

    override fun getItem(position: Int): Any {
        return movies[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
        var tview = view
        if (view == null) {
            tview = layoutInflator.inflate(R.layout.list_movies_layout, viewGroup, false)
        }

        val movie = movies[position]

        val img = tview?.findViewById<ImageView>(R.id.img_movie)
        val title = tview?.findViewById<TextView>(R.id.txt_movie_title)
        val type = tview?.findViewById<TextView>(R.id.txt_movie_type)

        var sizeTitle = 16
        if (movie.Title!!.length < sizeTitle) {
            sizeTitle = movie.Title.length
        }

        title?.let { it.text = movie.Title.substring(0, sizeTitle) }
        type?.let { it.text = movie.Type }

        Glide.with(tview!!)
            .load(movie.Poster)
            .placeholder(R.drawable.ic_launcher_foreground)
            .centerCrop()
            .into(img!!)

        tview.setOnClickListener {
            listener(movie)
        }

        return tview
    }
}