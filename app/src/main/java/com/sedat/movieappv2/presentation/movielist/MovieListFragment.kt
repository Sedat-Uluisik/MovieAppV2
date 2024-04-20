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
import com.sedat.movieappv2.presentation.movielanguage.LanguageBottomSheetDialogFragment
import com.sedat.movieappv2.util.hide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieListFragment : Fragment(R.layout.fragment_movie_list) {

    private lateinit var binding: FragmentMovieListBinding
    private val movieListViewModel: MovieListViewModel by viewModels()

    private val movieListAdapter = MovieListAdapter()

    private var isRefresh = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMovieListBinding.bind(view)

        setupCharacterRecyclerView()
        collectFromViewModel()
        itemListeners()
    }

    private fun itemListeners(){
        binding.imgLanguage.setOnClickListener{
            if(!isRefresh)
                LanguageBottomSheetDialogFragment{
                    isRefresh = true
                    binding.imgLanguage.setImageResource(R.drawable.ic_refresh_30)
                }.show(parentFragmentManager, "LanguageBottomSheetDialogFragment")
            else {
                movieListViewModel.getMovieList()
                binding.imgLanguage.setImageResource(R.drawable.ic_language_30)
                isRefresh = false
            }
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            movieListViewModel.getMovieList()
        }
    }

    private fun collectFromViewModel(){
        lifecycleScope.launch {
            launch {
                movieListViewModel.movieList.collectLatest{
                    movieListAdapter.submitData(it)
                    binding.swipeRefreshLayout.isRefreshing = false
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