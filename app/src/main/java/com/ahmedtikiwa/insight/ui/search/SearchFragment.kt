package com.ahmedtikiwa.insight.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ahmedtikiwa.insight.R
import com.ahmedtikiwa.insight.databinding.FragmentSearchBinding
import com.ahmedtikiwa.insight.domain.SearchResultArg
import com.google.android.material.snackbar.Snackbar
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
                    viewModel.onSearchRequestCompleted()
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
                    viewModel.onSearchRequestCompleted()
                } else {
                    binding.textinputSearch.error =
                        getString(R.string.search_screen_series_input_empty_error)
                }
            }
        })

        viewModel.movieSearchResult.observe(viewLifecycleOwner, { movieResult ->
            if (movieResult != null && movieResult.response == true) {
                binding.result = SearchResultArg(
                    poster = movieResult.poster,
                    title = movieResult.title,
                    year = movieResult.year,
                    imdbID = movieResult.imdbID,
                    response = movieResult.response,
                    error = movieResult.error
                )
            } else if (movieResult.response == false) {
                movieResult.error?.let {
                    Snackbar.make(
                        binding.root,
                        it, Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        })

        viewModel.seriesSearchResult.observe(viewLifecycleOwner, { seriesResult ->
            if (seriesResult != null && seriesResult.response == true) {
                binding.result = SearchResultArg(
                    poster = seriesResult.poster,
                    title = seriesResult.title,
                    year = seriesResult.year,
                    imdbID = seriesResult.imdbID,
                    response = seriesResult.response,
                    error = seriesResult.error
                )
            } else if (seriesResult.response == false) {
                seriesResult.error?.let {
                    Snackbar.make(
                        binding.root,
                        it, Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        })

        viewModel.selectedResult.observe(viewLifecycleOwner, { searchResult ->
            if (searchResult != null) {
                searchResult.imdbID?.let {
                    findNavController().navigate(
                        SearchFragmentDirections.actionSearchFragmentToDetailFragment(
                            it
                        )
                    )
                    viewModel.navigateToDetailComplete()
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}