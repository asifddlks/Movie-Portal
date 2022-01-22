package com.asifddlks.icinema.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asifddlks.icinema.databinding.ItemContinueWatchBinding

//
// Created by Asif Ahmed on 20/1/22.
//
class ContinueWatchAdapter() : RecyclerView.Adapter<ContinueWatchAdapter.ViewPagerViewHolder>() {

    private val dataList: List<String> =
        mutableListOf(
            "Inception",
            "Spider-Man",
            "Captain Marvel",
            "Inception",
            "Spider-Man",
            "Captain Marvel"
        )

    class ViewPagerViewHolder(val binding: ItemContinueWatchBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val binding =
            ItemContinueWatchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewPagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        holder.binding.textViewTitle.text = dataList[position]
    }

    override fun getItemCount(): Int = dataList.size
}