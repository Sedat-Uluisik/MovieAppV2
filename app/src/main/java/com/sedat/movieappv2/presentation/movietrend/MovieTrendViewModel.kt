package com.sedat.movieappv2.presentation.movietrend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sedat.movieappv2.data.remote.model.Movie
import com.sedat.movieappv2.domain.usecase.GetTrendMovies
import com.sedat.movieappv2.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieTrendViewModel @Inject constructor(
    private val getTrendMoviesUseCase: GetTrendMovies
): ViewModel() {

    private var _trendMovies: MutableStateFlow<Resource<Movie>> = MutableStateFlow(Resource.loading(null))
    var trendMovies = _trendMovies.asStateFlow()

    fun getTrendMovies(time: String) = viewModelScope.launch(Dispatchers.IO) {
        getTrendMoviesUseCase.invoke(time, 1).data?.let {
            _trendMovies.emit(Resource.success(it))
        }
    }

}