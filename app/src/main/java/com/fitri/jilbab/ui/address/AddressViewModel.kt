package com.fitri.jilbab.ui.address

import androidx.lifecycle.MutableLiveData
import com.commer.app.base.BaseViewModel
import com.fitri.jilbab.data.model.address.ListAddressResponse
import com.fitri.jilbab.data.model.address.add.AddAddressResponse
import com.fitri.jilbab.data.model.address.add.BodyAddAddress
import com.fitri.jilbab.data.model.address.cities.CitiesResponse
import com.fitri.jilbab.data.model.address.province.ProvinceResponse
import com.fitri.jilbab.repository.AddressRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val repository: AddressRepository
) : BaseViewModel() {

    val succesLoad = MutableLiveData<String>()
    val listAdress = MutableLiveData<ListAddressResponse>()
    val lisProvince = MutableLiveData<ProvinceResponse>()
    val listCity = MutableLiveData<CitiesResponse>()
    val addAddress = MutableLiveData<AddAddressResponse>()
    suspend fun isList() {
        repository.listAddress(
            onStart = {
                _loading.postValue(true)
            },
            onComplete = {
                _loading.postValue(false)
            },
            onError = {

            },
        ).collect {
            listAdress.postValue(it)
            succesLoad.postValue("200")
        }
    }

    suspend fun provList() {
        repository.lisProv(
            onStart = {
                _loading.postValue(true)
            },
            onComplete = {
                _loading.postValue(false)
            },
            onError = {

            },
        ).collect {
            lisProvince.postValue(it)
            succesLoad.postValue("200")
        }
    }

    suspend fun citiesList(
        id: Int
    ) {
        repository.listCity(
            onStart = {
                _loading.postValue(true)
            },
            onComplete = {
                _loading.postValue(false)
            },
            onError = {

            },
            id
        ).collect {
            listCity.postValue(it)
            succesLoad.postValue("200")
        }
    }

    suspend fun add(
        address: String,
        city: String,
        detail_address: String,
        is_main_address: Boolean,
        name: String,
        phone: String,
        province: String
    ) {
        val body = BodyAddAddress(
            address,
            city,
            detail_address,
            is_main_address,
            name,
            phone,
            province
        )
        repository.addAddress(
            onStart = {
                _loading.postValue(true)
            },
            onComplete = {
                _loading.postValue(false)
            },
            onError = {

            },
            body
            ).collect {
            addAddress.postValue(it)
            succesLoad.postValue("200")
        }
    }
}