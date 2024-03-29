package com.fitri.jilbab.ui.cart

import androidx.lifecycle.MutableLiveData
import com.fitri.jilbab.base.BaseViewModel
import com.fitri.jilbab.data.model.user.cart.add.AddCartResponse
import com.fitri.jilbab.data.model.user.cart.add.BodyCart
import com.fitri.jilbab.data.model.user.cart.list.CartResponse
import com.fitri.jilbab.data.model.user.cart.remove.RemoveResponse
import com.fitri.jilbab.data.model.user.newCo.CoBody
import com.fitri.jilbab.data.model.user.newCo.NewCoResponse
import com.fitri.jilbab.data.model.user.newOrder.NewOrderResponse
import com.fitri.jilbab.data.model.user.order.BodyPlaceOrder
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
    val pay = MutableLiveData<NewCoResponse>()
    val placeOrder = MutableLiveData<NewOrderResponse>()


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
        qty: String,
        color: String
    ) {
        val body = BodyCart(id_product, qty, color)
        repository.addCart(
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

    suspend fun checkout(
        id_ship_address: String
    ) {
        val body = CoBody(id_ship_address)
        repository.checkout(
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
            pay.postValue(it)
            succesLoad.postValue("200")
        }
    }

    suspend fun paymentMidtrans(
        body: BodyPlaceOrder
    ) {
       repository.placeOrder(
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
           placeOrder.postValue(it)
           succesLoad.postValue("200")
       }
    }

}