package com.fitri.jilbab.ui.address

import androidx.lifecycle.MutableLiveData
import com.commer.app.base.BaseViewModel
import com.fitri.jilbab.data.model.address.ListAddressResponse
import com.fitri.jilbab.repository.AddressRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val repository: AddressRepository
): BaseViewModel() {

    val succesLoad = MutableLiveData<String>()
    val listAdress = MutableLiveData<ListAddressResponse>()

    suspend fun isList(){
        repository.listAddress(
            onStart = {
                _loading.postValue(true)
            },
            onComplete = {
                _loading.postValue(false)
            },
            onError = {

            },
        ).collect{
            listAdress.postValue(it)
            succesLoad.postValue("200")
        }
    }
}