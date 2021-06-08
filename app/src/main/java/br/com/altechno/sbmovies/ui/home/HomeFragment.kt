package br.com.altechno.sbmovies.ui.home

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.altechno.sbmovies.R
import br.com.altechno.sbmovies.adapters.MoviesAdapter
import br.com.altechno.sbmovies.utils.movies
import com.bumptech.glide.Glide

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var dialog: Dialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnSearch = view.findViewById<ImageView>(R.id.img_search)

        btnSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }

        setupRecyclerViewMovies()
        setupRecyclerViewSeries()
        setupRecyclerViewGames()

        observerViewModels()

        if (movies.isEmpty()) {
            val listMovies = listOf<String>("rambo", "avengers", "marvel")
            homeViewModel.findMoviesSample(listMovies.random())
        }
    }

    private fun observerViewModels() {
        homeViewModel.moviesLiveData.observe(viewLifecycleOwner, { mvs ->
            movies = mvs

            val imgSlider = view?.findViewById<ImageView>(R.id.img_slider)

            Glide.with(requireView())
                .load(mvs.random().Poster)
                .into(imgSlider!!)


            setupRecyclerViewMovies()
            setupRecyclerViewSeries()
            setupRecyclerViewGames()
        })

        homeViewModel.getIsLoading().observeForever { status ->
            showLoading(status)
        }

        homeViewModel.getMessage().observeForever { message ->
            showMessageAlert(message)
        }
    }

    private fun setupRecyclerViewMovies() {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.recycler_movies)!!

        val mvs = movies.filter { it.Type == "movie" }.sortedBy { it.Title }

        if (mvs.isNotEmpty()) {
            recyclerView.visibility = View.VISIBLE
            view?.findViewById<TextView>(R.id.txt_movies)!!.visibility = View.VISIBLE

            recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = MoviesAdapter(mvs) { mv ->
                val args = Bundle()
                args.putParcelable("movie", mv)

                this.findNavController().navigate(R.id.action_homeFragment_to_detailsFragment, args)
            }
        } else {
            recyclerView.visibility = View.INVISIBLE
            view?.findViewById<TextView>(R.id.txt_movies)!!.visibility = View.INVISIBLE
        }
    }

    private fun setupRecyclerViewSeries() {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.recycler_series)!!

        val mvs = movies.filter { it.Type == "series" }.sortedBy { it.Title }

        if (mvs.isNotEmpty()) {
            recyclerView.visibility = View.VISIBLE
            view?.findViewById<TextView>(R.id.txt_series)!!.visibility = View.VISIBLE

            recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = MoviesAdapter(mvs) { mv ->
                val args = Bundle()
                args.putParcelable("movie", mv)

            this.findNavController().navigate(R.id.action_homeFragment_to_detailsFragment, args)
        }
    }

    private fun setupRecyclerViewChannels() {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.recycler_channels)!!

        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = MoviesAdapter(movies.sortedBy { it.Title }) { mv ->
            val args = Bundle()
            args.putParcelable("movie", mv)

            this.findNavController().navigate(R.id.action_homeFragment_to_detailsFragment, args)
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
        if (msg != "") {
            val builder = AlertDialog.Builder(requireContext())
            builder.setMessage(msg)
            val dlg = builder.create()
            dlg.show()
        }
    }
}