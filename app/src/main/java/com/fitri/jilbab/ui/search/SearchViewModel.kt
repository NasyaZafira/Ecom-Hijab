package com.fitri.jilbab.ui.search

import androidx.lifecycle.MutableLiveData
import com.commer.app.base.BaseViewModel
import com.fitri.jilbab.data.model.user.search.SearchResponse
import com.fitri.jilbab.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: UserRepository
): BaseViewModel() {
    val search = MutableLiveData<SearchResponse>()
    val succesLoad = MutableLiveData<String>()

    suspend fun searchProduct(
        q: String?
    ) {
        repository.searchProduct(
            onStart = {
                _loading.postValue(true)
            },
            onComplete = {
                _loading.postValue(false)
            },
            onError = {

            },
            q
        ).collect {
            search.postValue(it)
            succesLoad.postValue("200")
        }
    }



}