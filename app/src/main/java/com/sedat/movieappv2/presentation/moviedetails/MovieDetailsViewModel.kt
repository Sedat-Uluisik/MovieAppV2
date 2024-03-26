package com.sedat.movieappv2.presentation.moviedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sedat.movieappv2.data.remote.model.Movie
import com.sedat.movieappv2.data.remote.model.Result
import com.sedat.movieappv2.data.remote.model.imagemodel.MovieImages
import com.sedat.movieappv2.domain.usecase.GetMovieImages
import com.sedat.movieappv2.domain.usecase.GetMovieWithID
import com.sedat.movieappv2.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieWithIDUseCase: GetMovieWithID,
    private val getMovieImagesUseCase: GetMovieImages
): ViewModel() {

    private var _movie: MutableStateFlow<Resource<Result>> = MutableStateFlow(Resource.loading(null))
    var movie = _movie.asStateFlow()

    private var _movieImages: MutableStateFlow<Resource<MovieImages>> = MutableStateFlow(Resource.loading(null))
    var movieImages = _movieImages.asStateFlow()

    fun getMovieWithID(movieId: Int) = viewModelScope.launch(Dispatchers.IO) {
        getMovieWithIDUseCase.invoke(movieId).data?.let {
            _movie.emit(Resource.success(it))
        }
    }

    fun getMovieImages(movieId: Int) = viewModelScope.launch(Dispatchers.IO) {
        getMovieImagesUseCase.invoke(movieId).data?.let {
            _movieImages.emit(Resource.success(it))
        }
    }

}