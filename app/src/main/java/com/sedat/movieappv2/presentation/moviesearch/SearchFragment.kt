package com.sedat.movieappv2.presentation.moviesearch

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.sedat.movieappv2.R
import com.sedat.movieappv2.databinding.FragmentSearchBinding
import com.sedat.movieappv2.util.hide
import com.sedat.movieappv2.util.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private lateinit var binding: FragmentSearchBinding

    private val searchAdapter = SearchAdapter()
    private val searchViewModel: SearchViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSearchBinding.bind(view)
        setupCharacterRecyclerView()
        collectFromViewModel()
        search()

    }

    private fun setupCharacterRecyclerView() {
        binding.apply {
            recyclerSearch.apply {
                setHasFixedSize(true)
                adapter = searchAdapter
            }
        }
    }

    private fun collectFromViewModel(){
        lifecycleScope.launch {
            launch {
                searchViewModel.movies.collect{
                    searchAdapter.submitList(it.data?.results ?: listOf())
                    binding.progressBar.hide()
                }
            }
        }
    }

    private fun search(){
        var job: Job?= null
        binding.searchEditText.addTextChangedListener {
            job?.cancel()
            job = lifecycleScope.launch {
                delay(800)
                binding.progressBar.show()
                it?.let {
                    if(it.toString().isNotEmpty()){
                        searchViewModel.searchMovie(it.toString())
                    }else
                        binding.progressBar.hide()
                } ?: "clearData()"
            }
        }
    }


}