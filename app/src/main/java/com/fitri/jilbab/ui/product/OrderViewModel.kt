package com.fitri.jilbab.ui.product

import androidx.lifecycle.MutableLiveData
import com.commer.app.base.BaseViewModel
import com.fitri.jilbab.data.model.transaction.complete.CompleteResponse
import com.fitri.jilbab.data.model.transaction.cancle.CancleResponse
import com.fitri.jilbab.data.model.transaction.cancle.post.BodyCancleOrder
import com.fitri.jilbab.data.model.transaction.cancle.post.PostCancleResponse
import com.fitri.jilbab.data.model.transaction.complete.done.BodyDone
import com.fitri.jilbab.data.model.transaction.complete.done.DoneResponse
import com.fitri.jilbab.data.model.transaction.incoming.IncomingResponse
import com.fitri.jilbab.data.model.transaction.incoming.admin.AdmIncomResponse
import com.fitri.jilbab.data.model.transaction.incoming.admin.BodyStatusIncome
import com.fitri.jilbab.data.model.transaction.packed.PackedResponse
import com.fitri.jilbab.data.model.transaction.packed.admin.AdPackResponse
import com.fitri.jilbab.data.model.transaction.packed.admin.BodyPackedPost
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
    val postDone = MutableLiveData<DoneResponse>()
    val postPack = MutableLiveData<AdmIncomResponse>()
    val postSent = MutableLiveData<AdPackResponse>()


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
    suspend fun postDone(
        id_order : String,
        status_order : String
    ) {
        val body = BodyDone(status_order)
        repository.postDone(
            onStart = {
                _loading.postValue(true)
            },
            onComplete = {
                _loading.postValue(false)
            },
            onError = {

            },
            id_order,
            body
        ).collect {
            postDone.postValue(it)
        }
    }
    suspend fun postPacked(
        id_order : String,
        status_order : String
    ) {
        val body = BodyStatusIncome(status_order)
        repository.postPacked(
            onStart = {
                _loading.postValue(true)
            },
            onComplete = {
                _loading.postValue(false)
            },
            onError = {

            },
            id_order,
            body
        ).collect {
            postPack.postValue(it)
        }
    }
    suspend fun postSent(
        id_order : String,
        no_resi : String,
        status_order : String
    ) {
        val body = BodyPackedPost(no_resi,status_order)
        repository.postSent(
            onStart = {
                _loading.postValue(true)
            },
            onComplete = {
                _loading.postValue(false)
            },
            onError = {

            },
            id_order,
            body
        ).collect {
            postSent.postValue(it)
        }
    }
}