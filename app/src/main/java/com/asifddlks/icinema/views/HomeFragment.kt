package com.asifddlks.icinema.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            binding.textViewTitle.text = it
        })

        //testAPI()
        return root
    }

    private fun testAPI() {
        Log.d(TAG, "testAPI")
        ApiClient().get(requireContext(),
            "https://reqres.in/api/users?page=2",
            false,
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