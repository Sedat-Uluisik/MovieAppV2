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
    private val getLanguagesUseCase: GetLanguages
): ViewModel() {

    private val _movieList = MutableSharedFlow<PagingData<Result>>()
    val movieList = _movieList.asSharedFlow()

    private var _languages: MutableStateFlow<Resource<List<String>>> = MutableStateFlow(Resource.loading(null))
    var languages = _languages.asStateFlow()

    init {
        getMovieList()
        getLanguages()
    }

    private fun getMovieList(){
        getMovieListUseCase().onEach {
            _movieList.emit(it)
        }.launchIn(viewModelScope)
    }

    private fun getLanguages() = viewModelScope.launch(Dispatchers.IO) {
        getLanguagesUseCase.invoke().data?.let {
            _languages.emit(
                Resource.success(
                    it.map { languageItem ->
                        languageItem.iso6391
                    }
                )
            )
        }
    }
}