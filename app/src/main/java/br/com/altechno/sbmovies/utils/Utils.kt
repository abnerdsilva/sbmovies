package br.com.altechno.sbmovies.utils

import br.com.altechno.sbmovies.model.MovieSearch
import io.github.cdimascio.dotenv.dotenv

val appdotenv = dotenv {
    directory = "/assets "
    filename = "env"
}

var movies = listOf<MovieSearch>()