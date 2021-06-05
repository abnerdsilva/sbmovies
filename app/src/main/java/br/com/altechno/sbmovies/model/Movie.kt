package br.com.altechno.sbmovies.model

data class Movie(
    val title: String,
    val year: String,
    val rated: String?,
    val released: String?,
    val genre: String?,
    val director: String?,
    val writter: String?,
    val actos: String?,
    val plot: String?,
    val language: String?,
    val country: String?,
    val awards: String?,
    val poster: String?,
    val ratings: List<MovieRating>?,
    val metascore: String?,
    val imdbrating: String?,
    val imdbvotes: String?,
    val imdbID: String?,
    val type: String?,
    val dvd: String?,
    val boxOffice: String?,
    val production: String?,
    val website: String?,
    val response: String?
)
