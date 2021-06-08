package br.com.altechno.sbmovies.service

import br.com.altechno.sbmovies.model.Movie
import br.com.altechno.sbmovies.model.MovieSearchResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("/")
    fun findMoviesSearch(
        @Query("apiKey") key: String,
        @Query("s") search: String,
    ): Call<MovieSearchResult>

    @GET("/")
    fun findMovie(
        @Query("apiKey") key: String,
        @Query("t") title: String,
    ): Call<Movie>
}