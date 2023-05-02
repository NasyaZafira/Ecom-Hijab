package com.fitri.jilbab.ui.product

import androidx.lifecycle.MutableLiveData
import com.commer.app.base.BaseViewModel
import com.fitri.jilbab.data.model.transaction.complete.CompleteResponse
import com.fitri.jilbab.data.model.transaction.cancle.CancleResponse
import com.fitri.jilbab.data.model.transaction.cancle.post.BodyCancleOrder
import com.fitri.jilbab.data.model.transaction.cancle.post.PostCancleResponse
import com.fitri.jilbab.data.model.transaction.incoming.IncomingResponse
import com.fitri.jilbab.data.model.transaction.packed.PackedResponse
import com.fitri.jilbab.data.model.transaction.sent.SentResponse
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
    val packed = MutableLiveData<PackedResponse>()
    val sent = MutableLiveData<SentResponse>()
    val complete = MutableLiveData<CompleteResponse>()
    val cancle = MutableLiveData<CancleResponse>()
    val postcancle = MutableLiveData<PostCancleResponse>()


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
    suspend fun listPacked() {
        repository.listPacked(
            onStart = {
                _loading.postValue(true)
            },
            onComplete = {
                _loading.postValue(false)
            },
            onError = {

            },
        ).collect {
            packed.postValue(it)
        }
    }
    suspend fun lisSent() {
        repository.lisSent(
            onStart = {
                _loading.postValue(true)
            },
            onComplete = {
                _loading.postValue(false)
            },
            onError = {

            },
        ).collect {
            sent.postValue(it)
        }
    }
    suspend fun listComplete() {
        repository.listComplete(
            onStart = {
                _loading.postValue(true)
            },
            onComplete = {
                _loading.postValue(false)
            },
            onError = {

            },
        ).collect {
            complete.postValue(it)
        }
    }
    suspend fun listCancle() {
        repository.listCancle(
            onStart = {
                _loading.postValue(true)
            },
            onComplete = {
                _loading.postValue(false)
            },
            onError = {

            },
        ).collect {
            cancle.postValue(it)
        }
    }
    suspend fun postCancle(
        id_order : String
    ) {
        val body = BodyCancleOrder(id_order)
        repository.posCancle(
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
            postcancle.postValue(it)
        }
    }
}