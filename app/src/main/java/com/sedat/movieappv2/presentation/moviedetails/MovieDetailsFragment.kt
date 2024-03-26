package com.sedat.movieappv2.presentation.moviedetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.sedat.movieappv2.R
import com.sedat.movieappv2.databinding.FragmentMovieDetailsBinding
import com.sedat.movieappv2.util.hide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {

    private lateinit var binding: FragmentMovieDetailsBinding

    private val viewModel: MovieDetailsViewModel by viewModels()
    private val adapterMovieImages = MovieImagesAdapter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMovieDetailsBinding.bind(view)
        setupCharacterRecyclerView()
        collectFromViewModel()

        arguments?.let {
            val movieId = MovieDetailsFragmentArgs.fromBundle(it).movieid
            viewModel.getMovieWithID(movieId)
            viewModel.getMovieImages(movieId)
        }
    }

    private fun setupCharacterRecyclerView() {
        binding.apply {
            recyclerImages.apply {
                setHasFixedSize(true)
                adapter = adapterMovieImages
            }
        }
    }

    private fun collectFromViewModel(){
        lifecycleScope.launch {
            launch {
                viewModel.movie.collect{
                    binding.movie = it.data
                }
            }

            launch {
                viewModel.movieImages.collect{
                    adapterMovieImages.submitList(it.data?.backdrops ?: listOf())
                }
            }
        }
    }

}