package br.com.altechno.sbmovies.ui.details

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.altechno.sbmovies.R
import br.com.altechno.sbmovies.model.MovieSearch
import com.bumptech.glide.Glide

class DetailsFragment : Fragment(R.layout.fragment_details) {

    private lateinit var movieSearch: MovieSearch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            movieSearch = it.getParcelable("movie")!!
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val backButton: ImageView = view.findViewById(R.id.back_button)
        val poster = view.findViewById<ImageView>(R.id.img_details_poster)
        val txtTitle = view.findViewById<TextView>(R.id.txt_details_title)
        val txtYear = view.findViewById<TextView>(R.id.txt_details_year)
        val txtNoteImdb = view.findViewById<TextView>(R.id.txt_details_note_imdb)

        txtTitle.text = movieSearch.title
        txtYear.text = movieSearch.year
        txtNoteImdb.text = movieSearch.imdbID

        Glide.with(view)
            .load(movieSearch.poster)
            .centerInside()
            .into(poster)

        backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}