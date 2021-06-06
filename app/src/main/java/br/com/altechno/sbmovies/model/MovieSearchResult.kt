package br.com.altechno.sbmovies.model

data class MovieSearchResult(
    val Search: List<MovieSearch>?,
    val taotalResults: String?,
    val Response: String,
    val Error: String?
)
