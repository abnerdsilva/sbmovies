package br.com.altechno.sbmovies.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
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

        Glide.with(tview!!)
            .load(movie.Poster)
            .placeholder(R.drawable.sem_imagem)
            .centerCrop()
            .into(img!!)

        tview.setOnClickListener {
            listener(movie)
        }

        return tview
    }
}