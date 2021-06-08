package br.com.altechno.sbmovies.ui.details

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.altechno.sbmovies.R
import br.com.altechno.sbmovies.model.MovieSearch
import com.bumptech.glide.Glide

class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val detailsViewModel: DetailsViewModel by viewModels()

    private lateinit var dialog: Dialog
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

        backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        observerViewModels()
    }

    private fun observerViewModels() {
        detailsViewModel.moviesLiveData.observe(viewLifecycleOwner, { movie ->
            val poster = requireView().findViewById<ImageView>(R.id.img_details_poster)
            val txtTitle = requireView().findViewById<TextView>(R.id.txt_details_title)
            val txtYear = requireView().findViewById<TextView>(R.id.txt_details_year)
            val txtNoteImdb = requireView().findViewById<TextView>(R.id.txt_details_note_imdb)
            val txtGenre = requireView().findViewById<TextView>(R.id.txt_details_genre)
            val txtSinopse = requireView().findViewById<TextView>(R.id.txt_details_sinopse)
            val txtRuntime = requireView().findViewById<TextView>(R.id.txt_details_runtime)
            val txtLanguage = requireView().findViewById<TextView>(R.id.txt_details_language)
            val txtActors = requireView().findViewById<TextView>(R.id.txt_details_actors)

            if (movie.Runtime == "N/A") {
                txtRuntime.text = ""
            }

            txtTitle.text = movie.Title
            txtYear.text = movie.Year.replace("–", "")
            txtNoteImdb.text = "IMDb ${movie.imdbRating}"
            txtSinopse.text = movie.Plot
            txtGenre.text = movie.Genre
            txtLanguage.text = movie.Language
            txtActors.text = "Atores: ${movie.Actors}"

            Glide.with(requireView())
                .load(movie.Poster)
                .placeholder(R.drawable.sem_imagem)
                .centerInside()
                .into(poster)
        })

        detailsViewModel.getIsLoading().observeForever { status ->
            showLoading(status)
        }

        detailsViewModel.getMessage().observeForever { message ->
            showMessageAlert(message)
        }

        movieSearch.imdbID?.let {
            detailsViewModel.findMovie(it, movieSearch.Type!!, movieSearch.imdbID!!)
        }
    }

    private fun showLoading(statusLoading: Boolean) {
        if (statusLoading) {
            dialog = Dialog(requireContext())
            if (!dialog.isShowing) {
                dialog.setContentView(R.layout.loading)
                dialog.window!!.setLayout(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                dialog.show()
            }
        } else {
            if (dialog.isShowing) {
                dialog.dismiss()
            }
        }
    }

    private fun showMessageAlert(msg: String) {
        view?.let {
            val txtMessage: TextView = requireView().findViewById(R.id.txt_details_title)!!

            txtMessage.text = msg
        }
    }
}