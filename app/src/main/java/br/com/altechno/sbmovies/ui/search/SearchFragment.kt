package br.com.altechno.sbmovies.ui.search

import android.app.Dialog
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.altechno.sbmovies.R
import br.com.altechno.sbmovies.adapters.MoviesGridAdapter
import br.com.altechno.sbmovies.model.MovieSearch
import br.com.altechno.sbmovies.ui.home.HomeViewModel
import br.com.altechno.sbmovies.utils.movies

class SearchFragment : Fragment(R.layout.fragment_search) {

    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var dialog: Dialog

    private var waitingTime: Long = 800
    private var countDownTimer: CountDownTimer? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val backButton: ImageView = view.findViewById(R.id.back_button)
        val searchButton: SearchView = view.findViewById(R.id.searchView)

        searchButton.onActionViewExpanded()
        searchButton.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String): Boolean {
                if (text.isNotEmpty()) {
                    homeViewModel.findMoviesSample(text)
                }

                return true
            }

            override fun onQueryTextChange(text: String): Boolean {
                if (countDownTimer != null) {
                    countDownTimer!!.cancel()
                }
                countDownTimer = object : CountDownTimer(waitingTime, 500) {
                    override fun onTick(millisUntilFinished: Long) {
                        Log.d("TIME", "seconds remaining ($millisUntilFinished): " + millisUntilFinished / 1000)
                    }

                    override fun onFinish() {
                        Log.d("FINISHED", "DONE $text")

                        val mvs = listOf<MovieSearch>()
                        setupRecyclerViewMovies(mvs)

                        if (text.isNotEmpty()) {
                            homeViewModel.findMoviesSample(text)
                        } else {
                            showMessageAlert(text)
                        }
                    }
                }
                countDownTimer!!.start()

                return false
            }
        })


        backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        setupRecyclerViewMovies(movies)

        observerViewModels()
    }

    private fun observerViewModels() {
        homeViewModel.moviesLiveData.observe(viewLifecycleOwner, { mvs ->
            setupRecyclerViewMovies(mvs.sortedByDescending { it.Poster })
        })

        homeViewModel.getIsLoading().observeForever { status ->
            showLoading(status)
        }

        homeViewModel.getMessage().observeForever { message ->
            if (!message.contains("Too many results.")) {
                showMessageAlert(message)
            } else {
                val mv = listOf<MovieSearch>()
                setupRecyclerViewMovies(mv)
            }
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
            val txtMessage: TextView = requireView().findViewById(R.id.txt_search_message)!!

            txtMessage.text = msg
        }
    }

    private fun setupRecyclerViewMovies(mvs: List<MovieSearch>) {
        view?.let {
            val gridView: GridView = requireView().findViewById(R.id.grid_view_movies)!!

            gridView.adapter = MoviesGridAdapter(mvs, requireContext()) { mv ->
                val args = Bundle()
                args.putParcelable("movie", mv)

                this.findNavController().navigate(R.id.action_searchFragment_to_detailsFragment, args)

                val searchButton: SearchView = requireView().findViewById(R.id.searchView)
                searchButton.setQuery("", false)
            }
        }
    }
}