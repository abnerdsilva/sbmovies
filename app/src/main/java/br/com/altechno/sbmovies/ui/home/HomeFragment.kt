package br.com.altechno.sbmovies.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.altechno.sbmovies.R
import br.com.altechno.sbmovies.adapters.MoviesAdapter
import br.com.altechno.sbmovies.model.MovieSearch
import com.bumptech.glide.Glide

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imgSlider = view.findViewById<ImageView>(R.id.img_slider)

        Glide.with(view)
            .load(movies.random().poster)
            .into(imgSlider)

        val btnSearch = view.findViewById<ImageView>(R.id.img_search)

        btnSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }

        setupRecyclerViewMovies()
        setupRecyclerViewChannels()
    }

    private fun setupRecyclerViewMovies() {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.recycler_movies)!!

        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = MoviesAdapter(movies) { mv ->
            val args = Bundle()
            args.putParcelable("movie", mv)

            this.findNavController().navigate(R.id.action_homeFragment_to_detailsFragment, args)
        }
    }

    private fun setupRecyclerViewChannels() {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.recycler_channels)!!

        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = MoviesAdapter(movies.sortedBy { it.title }) { mv ->
            val args = Bundle()
            args.putParcelable("movie", mv)

            this.findNavController().navigate(R.id.action_homeFragment_to_detailsFragment, args)
        }
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
        )
    )
}