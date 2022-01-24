package com.asifddlks.icinema.constants

//
// Created by Asif Ahmed on 24/1/22.
//
class UrlConstants {
    val BASE_URL = "https://data-imdb1.p.rapidapi.com/"

    fun getSearchUrl(query: String): String {
        return BASE_URL + "movie/imdb_id/byTitle/${query}/"
    }

    fun getMovieDescUrl(id: String): String {
        return BASE_URL + "/movie/id/${id}/"
    }
}