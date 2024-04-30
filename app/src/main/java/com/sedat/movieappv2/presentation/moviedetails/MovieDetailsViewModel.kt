package com.sedat.movieappv2.presentation.moviedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sedat.movieappv2.data.remote.model.Result
import com.sedat.movieappv2.data.remote.model.imagemodel.MovieImages
import com.sedat.movieappv2.domain.usecase.local.DeleteFavouriteWithId
import com.sedat.movieappv2.domain.usecase.local.GetFavouriteWithId
import com.sedat.movieappv2.domain.usecase.local.SaveFavourite
import com.sedat.movieappv2.domain.usecase.remote.GetMovieImages
import com.sedat.movieappv2.domain.usecase.remote.GetMovieWithID
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
    private val getMovieImagesUseCase: GetMovieImages,
    private val saveFavouriteUseCase: SaveFavourite,
    private val getFavouriteWithIdUseCase: GetFavouriteWithId,
    private val deleteFavouriteWithIdUseCase: DeleteFavouriteWithId
): ViewModel() {

    private var _movie: MutableStateFlow<Resource<Result>> = MutableStateFlow(Resource.loading(null))
    var movie = _movie.asStateFlow()

    private var _movieImages: MutableStateFlow<Resource<MovieImages>> = MutableStateFlow(Resource.loading(null))
    var movieImages = _movieImages.asStateFlow()

    private var _isFavorite: MutableStateFlow<Boolean> = MutableStateFlow(false)
    var isFavorite = _isFavorite.asStateFlow()

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

    fun saveFavourite(result: Result) = viewModelScope.launch(Dispatchers.IO){
        saveFavouriteUseCase.invoke(result.toMovieEntity(isFavorite = true))
    }

    fun deleteFavourite(movieId: Int) = viewModelScope.launch(Dispatchers.IO){
        deleteFavouriteWithIdUseCase.invoke(movieId)
    }

    fun checkFavorite(movieId: Int) = viewModelScope.launch(Dispatchers.IO) {
        getFavouriteWithIdUseCase.invoke(movieId)?.let {
            _isFavorite.emit(true)
        } ?: _isFavorite.emit(false)
    }

}