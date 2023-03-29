package com.fitri.jilbab.ui.home

import androidx.lifecycle.MutableLiveData
import com.commer.app.base.BaseViewModel
import com.fitri.jilbab.data.model.user.DetailProductResponse
import com.fitri.jilbab.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: UserRepository
): BaseViewModel() {

    var detailProduct = MutableLiveData<DetailProductResponse>()

    suspend fun productUser(id_product: Int) {
        repository.detailProduct(
            onStart = {
                _loading.postValue(true) },
            onComplete = {
                _loading.postValue(false) },
            onError = {
                _message.postValue(it) },
            id_product
        ).collect {
            detailProduct.postValue(it)
        }
    }


}