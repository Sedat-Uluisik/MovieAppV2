package com.sedat.movieappv2.presentation.moviefavourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sedat.movieappv2.data.local.entity.MovieEntity
import com.sedat.movieappv2.domain.usecase.local.GetFavourites
import com.sedat.movieappv2.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieFavouritesViewModel @Inject constructor(
    private val getFavouritesUseCase: GetFavourites
): ViewModel() {

    private var _movieList: MutableStateFlow<Resource<List<MovieEntity>>> = MutableStateFlow(Resource.loading(null))
    var movieList = _movieList.asStateFlow()

    fun getFavourites() = viewModelScope.launch(Dispatchers.IO) {
        _movieList.emit(Resource.success(getFavouritesUseCase.invoke(50)))
    }
}