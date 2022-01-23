package com.asifddlks.icinema.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.asifddlks.icinema.R
import com.asifddlks.icinema.adapter.ContinueWatchAdapter
import com.asifddlks.icinema.adapter.HomeViewPagerAdapter
import com.asifddlks.icinema.databinding.FragmentHomeBinding
import com.asifddlks.icinema.network.ApiClient
import com.asifddlks.icinema.viewmodels.HomeViewModel

class HomeFragment : Fragment() {

    val TAG = this.javaClass.simpleName

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            binding.textViewTitle.text = it
        })

        initListeners()

        testAPI()
        loadViewPager()
        loadContinueWatch()
        return root
    }

    private fun initListeners() {
        binding.imageViewSearch.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_navigation_explore)
        }

        binding.imageViewNotification.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_notification_fragment)
        }

        binding.imageViewGenre.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_genre_fragment)
        }
    }

    private fun loadContinueWatch() {
        binding.recyclerViewContinueWatch.adapter = ContinueWatchAdapter()
    }

    private fun loadViewPager() {
        //binding.viewPager.adapter = HomeViewPagerAdapter()


        binding.viewPager.apply {
            offscreenPageLimit = 1
            val recyclerView = getChildAt(0) as RecyclerView
            recyclerView.apply {
                val padding = 80
                setPadding(padding, 0, padding, 0)
                clipToPadding = false
            }
            adapter = HomeViewPagerAdapter()
        }
        binding.indicator.setViewPager(binding.viewPager)
        binding.viewPager.currentItem = 1
    }

    private fun testAPI() {
        Log.d(TAG, "testAPI")
        ApiClient().get(
            requireContext(),
            "https://imdb-internet-movie-database-unofficial.p.rapidapi.com/search/inception",
            true,
            object : ApiClient.OnApiCallbackEventListener {
                override fun onSuccess(response: String?) {
                    Log.d(TAG, "onSuccess ${response}")
                }

                override fun onFailure(error: String?) {
                    Log.e(TAG, "onFailure ${error}")
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}