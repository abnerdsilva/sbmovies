package br.com.altechno.sbmovies.model

data class MovieSearch(
    val title: String,
    val year: String,
    val imdbID: String,
    val type: String,
    val poster: String,
)
