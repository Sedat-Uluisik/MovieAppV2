package com.sedat.movieappv2.presentation.moviedetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.sedat.movieappv2.R
import com.sedat.movieappv2.data.remote.model.Result
import com.sedat.movieappv2.databinding.FragmentMovieDetailsBinding
import com.sedat.movieappv2.interfaces.FavouriteBtnClickListener
import com.sedat.movieappv2.util.hide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details), FavouriteBtnClickListener {

    private lateinit var binding: FragmentMovieDetailsBinding

    private val viewModel: MovieDetailsViewModel by viewModels()
    private val adapterMovieImages = MovieImagesAdapter()

    private var isFavoriteControl = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMovieDetailsBinding.bind(view)
        binding.clickListener = this
        setupCharacterRecyclerView()
        collectFromViewModel()

        arguments?.let {
            val movieId = MovieDetailsFragmentArgs.fromBundle(it).movieid
            viewModel.getMovieWithID(movieId)
            viewModel.getMovieImages(movieId)
            viewModel.checkFavorite(movieId)
        }
    }

    private fun setupCharacterRecyclerView() {
        binding.apply {
            recyclerImages.apply {
                setHasFixedSize(true)
                adapter = adapterMovieImages
                set3DItem(true)
                setAlpha(true)
                setInfinite(false)
                setIntervalRatio(0.4f)
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

            launch {
                viewModel.isFavorite.collect{
                    binding.isFavorite = it
                    isFavoriteControl = it
                }
            }
        }
    }

    override fun onFavouriteBtnClick(view: View, movie: Result) {
        if(isFavoriteControl){
            viewModel.deleteFavourite(movie.id)
            binding.isFavorite = false
            isFavoriteControl = false
        }
        else{
            viewModel.saveFavourite(movie)
            binding.isFavorite = true
            isFavoriteControl = true
        }
    }

}