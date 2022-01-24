package com.asifddlks.icinema.model

data class MovieModel(
    val banner: String,
    val content_rating: String,
    val created_at: String,
    val description: String,
    val gen: List<Gen>,
    val image_url: String,
    val imdb_id: String,
    val keywords: List<Keyword>,
    val more_like_this: MoreLikeThis,
    val movie_length: Int,
    val plot: String,
    val popularity: Int,
    val rating: Double,
    val release: String,
    val title: String,
    val trailer: String,
    val type: String,
    val year: Int
) {
    data class Gen(
        val genre: String,
        val id: Int
    )

    data class Keyword(
        val id: Int,
        val keyword: String
    )

    class MoreLikeThis(
    )
}