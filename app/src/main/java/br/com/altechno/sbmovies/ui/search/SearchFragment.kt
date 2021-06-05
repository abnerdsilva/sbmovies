package br.com.altechno.sbmovies.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import br.com.altechno.sbmovies.R
import br.com.altechno.sbmovies.model.MovieSearch
import br.com.mrstecno.mymoviesimdb_test.adapters.MoviesGridAdapter

class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val backButton: ImageView = view.findViewById(R.id.back_button)

        backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        setupRecyclerViewMovies()
    }

    private fun setupRecyclerViewMovies() {
        val gridView: GridView = view?.findViewById(R.id.grid_view_movies)!!

        gridView.adapter = MoviesGridAdapter(movies, requireContext()) {}
    }

    private val movies = listOf(
        MovieSearch(
            title = "Person",
            poster = "https://www.pixsy.com/wp-content/uploads/2021/04/ben-sweet-2LowviVHZ-E-unsplash-1.jpeg",
            imdbID = "",
            type = "",
            year = ""
        ),
        MovieSearch(
            title = "Star Wars",
            poster = "https://miro.medium.com/max/1838/1*mk1-6aYaf_Bes1E3Imhc0A.jpeg",
            imdbID = "",
            type = "",
            year = ""
        ),
        MovieSearch(
            title = "Butterfly",
            poster = "https://helpx.adobe.com/content/dam/help/en/stock/how-to/visual-reverse-image-search/jcr_content/main-pars/image/visual-reverse-image-search-v2_intro.jpg",
            imdbID = "",
            type = "",
            year = ""
        ),
        MovieSearch(
            title = "Birds",
            poster = "https://ep00.epimg.net/verne/imagenes/2019/11/13/album/1573641411_551713_1573641467_album_normal.jpg",
            imdbID = "",
            type = "",
            year = ""
        ),
        MovieSearch(
            title = "Star Wars",
            poster = "https://miro.medium.com/max/1838/1*mk1-6aYaf_Bes1E3Imhc0A.jpeg",
            imdbID = "",
            type = "",
            year = ""
        ),
        MovieSearch(
            title = "Butterfly",
            poster = "https://helpx.adobe.com/content/dam/help/en/stock/how-to/visual-reverse-image-search/jcr_content/main-pars/image/visual-reverse-image-search-v2_intro.jpg",
            imdbID = "",
            type = "",
            year = ""
        ),
        MovieSearch(
            title = "Birds",
            poster = "https://ep00.epimg.net/verne/imagenes/2019/11/13/album/1573641411_551713_1573641467_album_normal.jpg",
            imdbID = "",
            type = "",
            year = ""
        ),
        MovieSearch(
            title = "Star Wars",
            poster = "https://miro.medium.com/max/1838/1*mk1-6aYaf_Bes1E3Imhc0A.jpeg",
            imdbID = "",
            type = "",
            year = ""
        ),
        MovieSearch(
            title = "Butterfly",
            poster = "https://helpx.adobe.com/content/dam/help/en/stock/how-to/visual-reverse-image-search/jcr_content/main-pars/image/visual-reverse-image-search-v2_intro.jpg",
            imdbID = "",
            type = "",
            year = ""
        ),
        MovieSearch(
            title = "Birds",
            poster = "https://ep00.epimg.net/verne/imagenes/2019/11/13/album/1573641411_551713_1573641467_album_normal.jpg",
            imdbID = "",
            type = "",
            year = ""
        )
    )
}