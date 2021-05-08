package com.ahmedtikiwa.insight.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ahmedtikiwa.insight.R
import com.ahmedtikiwa.insight.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<SearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater)

        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.movieSearchRequest.observe(viewLifecycleOwner, {
            if (it) {
                val query = binding.textinputSearch.editText?.text.toString().trim()
                if (query.isNotBlank()) {
                    binding.textinputSearch.error = null
                    viewModel.onMovieQueryReceived(query)
                } else {
                    binding.textinputSearch.error =
                        getString(R.string.search_screen_movie_input_empty_error)
                }
            }
        })

        viewModel.seriesSearchRequest.observe(viewLifecycleOwner, {
            if (it) {
                val query = binding.textinputSearch.editText?.text.toString().trim()
                if (query.isNotBlank()) {
                    binding.textinputSearch.error = null
                    viewModel.onSeriesQueryReceived(query)
                } else {
                    binding.textinputSearch.error =
                        getString(R.string.search_screen_series_input_empty_error)
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}