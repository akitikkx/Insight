package com.ahmedtikiwa.insight.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmedtikiwa.insight.R
import com.ahmedtikiwa.insight.databinding.FragmentSearchBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment(), SearchResultsAdapter.SearchResultsAdapterListener {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var searchViewModelFactory: SearchViewModel.SearchViewModelFactory

    private lateinit var searchResultsAdapter: SearchResultsAdapter

    private val viewModel by viewModels<SearchViewModel> {
        searchViewModelFactory.create(this)
    }

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

        searchResultsAdapter = SearchResultsAdapter(this)

        binding.apply {
            searchResults.setHasFixedSize(true)
            searchResults.adapter = searchResultsAdapter.withLoadStateHeaderAndFooter(
                header = SearchResultsLoadStateAdapter { searchResultsAdapter.retry() },
                footer = SearchResultsLoadStateAdapter { searchResultsAdapter.retry() }
            )
            searchResults.layoutManager = LinearLayoutManager(requireContext()).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
        }

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
            searchResultsAdapter.submitData(viewLifecycleOwner.lifecycle, movieResult)
        })

        viewModel.seriesSearchResult.observe(viewLifecycleOwner, { seriesResult ->
            searchResultsAdapter.submitData(viewLifecycleOwner.lifecycle, seriesResult)
        })

        lifecycleScope.launch {
            searchResultsAdapter.loadStateFlow.collectLatest { loadState ->
                if (loadState.refresh is LoadState.Loading) {
                    Snackbar.make(
                        binding.root,
                        getString(R.string.list_loading_message),
                        Snackbar.LENGTH_LONG
                    ).show()
                } else if (loadState.refresh is LoadState.Error) {
                    val error = (loadState.refresh as LoadState.Error).error.message
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.list_loading_error, error),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        viewModel.error.observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                Snackbar.make(
                    binding.root,
                    it, Snackbar.LENGTH_LONG
                ).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResultClick(view: View, imdbId: String) {
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToDetailFragment(
                imdbId
            )
        )
    }
}