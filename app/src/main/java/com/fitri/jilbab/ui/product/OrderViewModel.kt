package com.fitri.jilbab.ui.product

import androidx.lifecycle.MutableLiveData
import com.commer.app.base.BaseViewModel
import com.fitri.jilbab.data.model.transaction.incoming.IncomingResponse
import com.fitri.jilbab.data.model.transaction.unpaid.UnpaidResponse
import com.fitri.jilbab.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val repository: ProductRepository
) : BaseViewModel() {

    val unpaid = MutableLiveData<UnpaidResponse>()
    val incom = MutableLiveData<IncomingResponse>()

    suspend fun listUnpaid() {
        repository.listUnpaid(
            onStart = {
                _loading.postValue(true)
            },
            onComplete = {
                _loading.postValue(false)
            },
            onError = {

            },
        ).collect {
            unpaid.postValue(it)
        }
    }

    suspend fun listIncom() {
        repository.listIncom(
            onStart = {
                _loading.postValue(true)
            },
            onComplete = {
                _loading.postValue(false)
            },
            onError = {

            },
        ).collect {
            incom.postValue(it)
        }
    }
}