package br.com.altechno.sbmovies.ui.search

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.altechno.sbmovies.R
import br.com.altechno.sbmovies.utils.movies
import br.com.mrstecno.mymoviesimdb_test.adapters.MoviesGridAdapter

class SearchFragment : Fragment() {

    private var waitingTime: Long = 500
    private var countDownTimer: CountDownTimer? = null

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
        val searchButton: SearchView = view.findViewById(R.id.searchView)

        searchButton.onActionViewExpanded()
        searchButton.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String): Boolean {
                if (text.isNotEmpty()) {
                    Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
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
                        if (text.isNotEmpty()) {
                            Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
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

        setupRecyclerViewMovies()
    }

    private fun setupRecyclerViewMovies() {
        val gridView: GridView = view?.findViewById(R.id.grid_view_movies)!!

        gridView.adapter = MoviesGridAdapter(movies, requireContext()) { mv ->
            val args = Bundle()
            args.putParcelable("movie", mv)

            this.findNavController().navigate(R.id.action_searchFragment_to_detailsFragment, args)
        }
    }
}