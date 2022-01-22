package com.asifddlks.icinema

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.asifddlks.icinema.databinding.NotificationFragmentBinding

class NotificationFragment : Fragment() {

    private lateinit var viewModel: NotificationViewModel

    private var _binding: NotificationFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return inflater.inflate(R.layout.notification_fragment, container, false)
        viewModel = ViewModelProvider(this).get(NotificationViewModel::class.java)

        _binding = NotificationFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.imageViewBack.setOnClickListener {
            findNavController().popBackStack()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}