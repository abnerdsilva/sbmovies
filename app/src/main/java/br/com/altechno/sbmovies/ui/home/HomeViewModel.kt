package br.com.altechno.sbmovies.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.altechno.sbmovies.model.MovieSearch
import br.com.altechno.sbmovies.model.MovieSearchResult
import br.com.altechno.sbmovies.utils.RetrofitInitializer
import br.com.altechno.sbmovies.utils.appdotenv
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    val moviesLiveData: MutableLiveData<List<MovieSearch>> = MutableLiveData()

    private val isLoading = MutableLiveData<Boolean>()
    private val message = MutableLiveData<String>()

    fun findMoviesSample(search: String) {
        isLoading.value = true
        message.value = ""

        try {
            CoroutineScope(Dispatchers.IO).launch {
                val apiKey = appdotenv["SECRET_KEY"]

                val response = async {
                    RetrofitInitializer().movieService()
                        .findMoviesSearch(apiKey, search)
                }
                val list = response.await()

                withContext(Dispatchers.Main) {
                    list.enqueue(object : Callback<MovieSearchResult> {
                        override fun onFailure(call: Call<MovieSearchResult>, t: Throwable) {
                            Log.e("ERROR", t.message.toString())
                            message.value = t.message
                            isLoading.value = false
                        }

                        override fun onResponse(
                            call: Call<MovieSearchResult>,
                            response: Response<MovieSearchResult>
                        ) {
                            response.body()?.let { rs ->
                                if (rs.Response == "True") {
                                    moviesLiveData.value = rs.Search
                                } else {
                                    message.value = rs.Error
                                }
                            }

                            isLoading.value = false
                        }
                    })
                }
            }
        } catch (e: Exception) {
            Log.e("ERROR", e.message.toString())
            message.value = e.message
            isLoading.value = false
        }
    }

    fun getMessage(): LiveData<String> {
        return message
    }

    fun getIsLoading(): LiveData<Boolean> {
        return isLoading
    }
}