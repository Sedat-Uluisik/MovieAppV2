package com.sedat.movieappv2.presentation.movielist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import com.sedat.movieappv2.data.remote.model.Movie
import com.sedat.movieappv2.data.remote.model.Result
import com.sedat.movieappv2.domain.usecase.remote.GetLanguages
import com.sedat.movieappv2.domain.usecase.remote.GetMovieList
import com.sedat.movieappv2.util.Resource
import com.sedat.movieappv2.util.SharedPrefsLanguage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val getMovieListUseCase: GetMovieList,
    private val sharedPrefsLanguage: SharedPrefsLanguage
): ViewModel() {

    private val _movieList = MutableSharedFlow<PagingData<Result>>()
    val movieList = _movieList.asSharedFlow()

    private val _language = MutableStateFlow<String>("")
    val language = _language.asSharedFlow()

    init {
        getMovieList()
    }

    fun getMovieList(){
        getMovieListUseCase().onEach {
            _movieList.emit(it)
        }.launchIn(viewModelScope)
    }

    fun getLanguage() = viewModelScope.launch(Dispatchers.IO){
        val language = sharedPrefsLanguage.getLanguage()
        _language.emit(language)
    }

}