package com.sedat.movieappv2.presentation.movietrend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.sedat.movieappv2.R
import com.sedat.movieappv2.databinding.FragmentMovieTrendBinding
import com.sedat.movieappv2.presentation.movielist.MovieListAdapter
import com.sedat.movieappv2.util.hide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieTrendFragment : Fragment(R.layout.fragment_movie_trend) {

    private lateinit var binding: FragmentMovieTrendBinding

    private val trendAdapter = MovieTrendAdapter()
    private val viewModel: MovieTrendViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMovieTrendBinding.bind(view)
        setupCharacterRecyclerView()
        collectFromViewModel()

        viewModel.getTrendMovies("day")

        binding.btnSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked)
                viewModel.getTrendMovies("day")
            else
                viewModel.getTrendMovies("week")
        }
    }

    private fun setupCharacterRecyclerView() {
        binding.apply {
            recyclerTrending.apply {
                setHasFixedSize(true)
                adapter = trendAdapter
            }
        }
    }

    private fun collectFromViewModel(){
        lifecycleScope.launch {
            launch {
                viewModel.trendMovies.collect{
                    trendAdapter.submitData(PagingData.from(it.data?.results ?: listOf()))
                    binding.progressBarTrend.hide()
                }
            }
        }
    }

}