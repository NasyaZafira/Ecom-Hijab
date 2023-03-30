package com.fitri.jilbab.ui.cart

import androidx.lifecycle.MutableLiveData
import com.commer.app.base.BaseViewModel
import com.fitri.jilbab.data.model.user.cart.add.AddCartResponse
import com.fitri.jilbab.data.model.user.cart.add.BodyCart
import com.fitri.jilbab.data.model.user.cart.list.CartResponse
import com.fitri.jilbab.data.model.user.cart.remove.RemoveResponse
import com.fitri.jilbab.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: UserRepository
) : BaseViewModel() {
    val cart = MutableLiveData<CartResponse>()
    val succesLoad = MutableLiveData<String>()
    val remove = MutableLiveData<RemoveResponse>()
    val add = MutableLiveData<AddCartResponse>()


    suspend fun cartList() {
        repository.cartProduct(
            onStart = {
                _loading.postValue(true)
            },
            onComplete = {
                _loading.postValue(false)
            },
            onError = {

            },
        ).collect {
            cart.postValue(it)
            succesLoad.postValue("200")
        }
    }

    suspend fun removeCart(
        id_cart: Int
    ) {
        repository.removeCart(
            onStart = {
                _loading.postValue(true)
            },
            onComplete = {
                _loading.postValue(false)
            },
            onError = {

            },
            id_cart
        ).collect {
            remove.postValue(it)
            succesLoad.postValue("200")
        }
    }

    suspend fun addCart(
        id_product: String,
        qty: String
    ) {
        val body = BodyCart(id_product, qty)
        repository.removeCart(
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
            add.postValue(it)
            succesLoad.postValue("200")
        }
    }

}