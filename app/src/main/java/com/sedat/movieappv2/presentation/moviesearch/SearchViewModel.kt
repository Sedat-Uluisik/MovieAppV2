package com.sedat.movieappv2.presentation.moviesearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sedat.movieappv2.data.remote.model.Movie
import com.sedat.movieappv2.domain.usecase.remote.SearchMovie
import com.sedat.movieappv2.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMovieUseCase: SearchMovie
): ViewModel() {

    private var _movies: MutableStateFlow<Resource<Movie>> = MutableStateFlow(Resource.loading(null))
    var movies = _movies.asStateFlow()

    fun searchMovie(query: String) = viewModelScope.launch(Dispatchers.IO) {
        searchMovieUseCase.invoke(query).data?.let {
            _movies.emit(Resource.success(it))
        }
    }
}