package com.sedat.movieappv2.presentation.moviefavourites

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import com.sedat.movieappv2.R
import com.sedat.movieappv2.databinding.FragmentMovieFavouritesBinding
import com.sedat.movieappv2.presentation.movielist.MovieListFragmentDirections
import com.sedat.movieappv2.util.hide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieFavouritesFragment : Fragment(R.layout.fragment_movie_favourites) {

    private lateinit var binding: FragmentMovieFavouritesBinding

    private val adapterFavourites = MovieFavouritesAdapter()
    private val viewModel: MovieFavouritesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMovieFavouritesBinding.bind(view)

        itemListeners()
        collectFromViewModel()
        setupCharacterRecyclerView()
        viewModel.getFavourites()
    }

    private fun setupCharacterRecyclerView() {
        binding.apply {
            recyclerFavourites.apply {
                setHasFixedSize(true)
                adapter = adapterFavourites
            }
        }
    }

    private fun itemListeners(){
        adapterFavourites.setOnItemClickListener {
            val action = MovieFavouritesFragmentDirections.actionMovieFavouritesFragmentToMovieDetailsFragment(movieid = it)
            findNavController().navigate(action)
        }
    }

    private fun collectFromViewModel(){
        lifecycleScope.launch {
            launch {
                viewModel.movieList.collect{
                    adapterFavourites.submitData(PagingData.from(it.data ?: listOf()))
                }
            }
        }

    }

}