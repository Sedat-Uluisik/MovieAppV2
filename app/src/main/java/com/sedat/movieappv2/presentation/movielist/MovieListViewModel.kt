package com.sedat.movieappv2.presentation.movielist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sedat.movieappv2.data.remote.model.LanguageItem
import com.sedat.movieappv2.data.remote.model.Movie
import com.sedat.movieappv2.domain.usecase.GetLanguages
import com.sedat.movieappv2.domain.usecase.GetMovieList
import com.sedat.movieappv2.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val getMovieListUseCase: GetMovieList,
    private val getLanguagesUseCase: GetLanguages
): ViewModel() {
    private var _movies: MutableStateFlow<Resource<Movie>> = MutableStateFlow(Resource.loading(null))
    var movies = _movies.asStateFlow()

    private var _languages: MutableStateFlow<Resource<List<String>>> = MutableStateFlow(Resource.loading(null))
    var languages = _languages.asStateFlow()

    init {
        getMovieList()
        getLanguages()
    }

    private fun getMovieList() = viewModelScope.launch(Dispatchers.IO) {
        getMovieListUseCase(1).data?.let {
            _movies.emit(Resource.success(it))
        }
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