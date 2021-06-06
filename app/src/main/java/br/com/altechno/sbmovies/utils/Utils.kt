package br.com.altechno.sbmovies.utils

import io.github.cdimascio.dotenv.dotenv

val appdotenv = dotenv {
    directory = "/assets "
    filename = "env"
}