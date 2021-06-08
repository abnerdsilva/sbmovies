package br.com.altechno.sbmovies.ui.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.altechno.sbmovies.model.Movie
import br.com.altechno.sbmovies.utils.RetrofitInitializer
import br.com.altechno.sbmovies.utils.appdotenv
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsViewModel: ViewModel() {

    val moviesLiveData: MutableLiveData<Movie> = MutableLiveData()

    private val isLoading = MutableLiveData<Boolean>()
    private val message = MutableLiveData<String>()

    fun findMovie(title: String, type: String, id: String) {
        isLoading.value = true
        message.value = ""

        try {
            CoroutineScope(Dispatchers.IO).launch {
                val apiKey = appdotenv["SECRET_KEY"]

                val response = async {
                    if (id == "") {
                        RetrofitInitializer().movieService()
                            .findMovie(apiKey, title, type)
                    } else {
                        RetrofitInitializer().movieService()
                            .findMovieByID(apiKey, id)
                    }
                }
                val list = response.await()

                withContext(Dispatchers.Main) {
                    list.enqueue(object : Callback<Movie> {
                        override fun onFailure(call: Call<Movie>, t: Throwable) {
                            Log.e("ERROR", t.message.toString())
                            message.value = t.message
                            isLoading.value = false
                        }

                        override fun onResponse(
                            call: Call<Movie>,
                            response: Response<Movie>
                        ) {
                            response.body()?.let { rs ->
                                if (rs.Response == "True") {
                                    moviesLiveData.value = rs
                                }
                            }

                            response.errorBody()?.let {
                                message.value = response.message()
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