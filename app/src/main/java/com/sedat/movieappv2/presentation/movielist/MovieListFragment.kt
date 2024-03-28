package com.sedat.movieappv2.presentation.movielist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.sedat.movieappv2.R
import com.sedat.movieappv2.databinding.FragmentMovieListBinding
import com.sedat.movieappv2.util.hide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieListFragment : Fragment(R.layout.fragment_movie_list) {

    private lateinit var binding: FragmentMovieListBinding
    private val movieListViewModel: MovieListViewModel by viewModels()

    private val movieListAdapter = MovieListAdapter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMovieListBinding.bind(view)

        setupCharacterRecyclerView()
        collectFromViewModel()
        itemListeners()
    }

    private fun itemListeners(){
        movieListAdapter.setOnItemClickListener {
            val action = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailsFragment(movieid = it)
            findNavController().navigate(action)
        }
    }

    private fun collectFromViewModel(){
        lifecycleScope.launch {
            launch {
                movieListViewModel.movieList.collectLatest{
                    movieListAdapter.submitData(it)
                    binding.progressBar.hide()
                }
            }

            launch {
                movieListViewModel.languages.collect{
                    val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, it.data ?: listOf())
                    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.spinner.adapter = spinnerAdapter
                }
            }
        }

    }

    private fun setupCharacterRecyclerView() {
        binding.apply {
             recylerMovieList.apply {
                setHasFixedSize(true)
                adapter = movieListAdapter
            }
        }
    }


}