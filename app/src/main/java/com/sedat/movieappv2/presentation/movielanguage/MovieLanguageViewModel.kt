package com.sedat.movieappv2.presentation.movielanguage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sedat.movieappv2.data.remote.model.LanguageItem
import com.sedat.movieappv2.domain.usecase.remote.GetLanguages
import com.sedat.movieappv2.util.Resource
import com.sedat.movieappv2.util.SharedPrefsLanguage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieLanguageViewModel @Inject constructor(
    private val getLanguagesUseCase: GetLanguages,
    private val sharedPrefsLanguage: SharedPrefsLanguage
): ViewModel() {

    private var _languages: MutableStateFlow<Resource<List<LanguageItem>>> = MutableStateFlow(Resource.loading(null))
    var languages = _languages.asStateFlow()

    init {
        getLanguages()
    }

    private fun getLanguages() = viewModelScope.launch(Dispatchers.IO) {
        getLanguagesUseCase.invoke().data?.let { list ->
            _languages.emit(
                Resource.success(
                    list.sortedBy { it.iso6391 }
                )
            )
        }
    }

    fun selectLanguage(lang: String) = viewModelScope.launch(Dispatchers.IO){
        val updatedList = languages.value.data?.let {list ->
            list.map {languageItem ->
                if(languageItem.iso6391 == lang)
                    languageItem.copy(isSelect = true)
                else
                    languageItem.copy(isSelect = false)
            }
        }

        sharedPrefsLanguage.saveLanguage(lang)

        _languages.value = Resource.success(updatedList)
    }
}