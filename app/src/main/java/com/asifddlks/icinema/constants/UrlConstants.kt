package com.asifddlks.icinema.constants

//
// Created by Asif Ahmed on 24/1/22.
//
class UrlConstants {
    val BASE_URL = "https://imdb-internet-movie-database-unofficial.p.rapidapi.com/search/inception"

    fun getSearchUrl(query: String): String? {
        //String url = "http://wellcall.thebitlabs.com/api/HoorayHealthProviders/get?lat=33.32027&lng=-111.89226&distance_in_km=10&types=retail%20clinic,urgent%20care";
        return BASE_URL + "search/${query}"
    }
}