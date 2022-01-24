package com.asifddlks.icinema.views

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.asifddlks.icinema.R
import com.asifddlks.icinema.adapter.MovieSearchAdapter
import com.asifddlks.icinema.databinding.FragmentExploreSearchResultBinding
import com.asifddlks.icinema.helpers.DialogHelper
import com.asifddlks.icinema.viewmodels.ExploreSearchResultViewModel


class ExploreSearchResultFragment : Fragment() {

    val TAG = this.javaClass.simpleName

    private lateinit var viewModel: ExploreSearchResultViewModel
    private var _binding: FragmentExploreSearchResultBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val adapter = MovieSearchAdapter()

    lateinit var dialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(ExploreSearchResultViewModel::class.java)

        _binding = FragmentExploreSearchResultBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.editTextSearch.setText(arguments?.getString("query"))

        binding.recyclerView.adapter = adapter

        initListeners()

        viewModel.movieList.observe(viewLifecycleOwner, Observer {
            adapter.setMovieList(it)
            dialog.dismiss()
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            dialog.dismiss()
        })

        dialog = DialogHelper(requireContext()).showLoaderDialog()
        dialog.show()
        viewModel.searchMovie(binding.editTextSearch.text.toString())



        return root
    }

    private fun initListeners() {
        binding.buttonCancel.setOnClickListener {
            if (binding.buttonCancel.text.equals(getString(R.string.cancel))) {
                findNavController().popBackStack()
            } else {

            }

        }

        binding.editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.length > 0) {
                    binding.buttonCancel.text = getString(R.string.search)
                } else {
                    binding.buttonCancel.text = getString(R.string.cancel)
                }

            }
        })

        binding.editTextSearch.setOnEditorActionListener { view, actionId, keyEvent ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

            }
            handled
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}