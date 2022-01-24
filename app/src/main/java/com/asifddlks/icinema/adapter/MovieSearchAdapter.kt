package com.asifddlks.icinema.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asifddlks.icinema.R
import com.asifddlks.icinema.constants.UrlConstants
import com.asifddlks.icinema.databinding.ItemMovieSearchBinding
import com.asifddlks.icinema.model.MovieModel
import com.asifddlks.icinema.model.TitleModel
import com.asifddlks.icinema.network.ApiClient
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.gson.Gson
import org.json.JSONObject

//
// Created by Asif Ahmed on 20/1/22.
//
class MovieSearchAdapter() : RecyclerView.Adapter<MovieSearchAdapter.ViewPagerViewHolder>() {

    val TAG = this.javaClass.simpleName
    var movies = mutableListOf<TitleModel>()

    fun setMovieList(movies: List<TitleModel>) {
        this.movies = movies.toMutableList()
        notifyDataSetChanged()
    }

    class ViewPagerViewHolder(val binding: ItemMovieSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val binding =
            ItemMovieSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewPagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        holder.binding.textViewTitle.text = movies[position].imdb_id
        movieDesc(holder.binding, movies[position].imdb_id)
    }

    private fun movieDesc(binding: ItemMovieSearchBinding, imdbId: String) {
        ApiClient().get(
            UrlConstants().getMovieDescUrl(imdbId),
            true,
            object : ApiClient.OnApiCallbackEventListener {
                override fun onSuccess(response: String?) {

                    val jsonObj = JSONObject(response)
                    val results = jsonObj.getJSONObject("results")
                    val gson = Gson()
                    val movie = gson?.fromJson(results.toString(), MovieModel::class.java)
                    Log.d(TAG, "onSuccess ${movie.image_url}")

                    binding.textViewRating.text = "${movie.rating}"

                    var genre = "";
                    for (gen in movie.gen) {
                        genre = "${gen.genre},"
                    }
                    if (genre.endsWith(",")) {
                        genre.dropLast(1)
                    }

                    binding.apply {
                        textViewTitle.text = movie.title

                        //movie.gen.toString()
                        textViewDesc.text = "${movie.year} | ${genre}"

                        Glide.with(root)
                            .load(movie.image_url)
                            .centerCrop()
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .error(R.drawable.ic_error)
                            .into(imageViewPoster)


                    }

                }

                override fun onFailure(error: String?) {

                }

            })
    }

    override fun getItemCount(): Int = movies.size
}