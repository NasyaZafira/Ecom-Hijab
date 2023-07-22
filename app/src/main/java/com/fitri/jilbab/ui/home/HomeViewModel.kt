package com.fitri.jilbab.ui.home

import androidx.lifecycle.MutableLiveData
import com.fitri.jilbab.base.BaseViewModel
import com.fitri.jilbab.data.model.user.latestDt.DtLatestResponse
import com.fitri.jilbab.data.model.user.review.BodyReview
import com.fitri.jilbab.data.model.user.review.ReviewResponse
import com.fitri.jilbab.data.model.user.review.list.ListReviewResponse
import com.fitri.jilbab.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: UserRepository
) : BaseViewModel() {

    var detailProduct = MutableLiveData<DtLatestResponse>()
    var isReview = MutableLiveData<ReviewResponse>()
    var listReview = MutableLiveData<ListReviewResponse>()

    suspend fun productUser(id_product: Int) {
        repository.detailProduct(
            onStart = {
                _loading.postValue(true)
            },
            onComplete = {
                _loading.postValue(false)
            },
            onError = {
                _message.postValue(it)
            },
            id_product
        ).collect {
            detailProduct.postValue(it)
        }
    }

    suspend fun listReview(id_product: String) {
        repository.listReview(
            onStart = {
                _loading.postValue(true)
            },
            onComplete = {
                _loading.postValue(false)
            },
            onError = {
                _message.postValue(it)
            },
            id_product
        ).collect {
            listReview.postValue(it)
        }
    }

    suspend fun reviewProduct(
        id_product: String,
        rating: String,
        review: String
    ) {
        val body = BodyReview(id_product, rating, review)
        repository.addReview(
            onStart = {
                _loading.postValue(true)
            },
            onComplete = {
                _loading.postValue(false)
            },
            onError = {
                _message.postValue(it)
            },
            body
        ).collect {
            isReview.postValue(it)
        }
    }


}