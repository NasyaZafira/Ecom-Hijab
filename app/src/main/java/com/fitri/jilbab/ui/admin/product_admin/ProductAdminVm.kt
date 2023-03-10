package com.fitri.jilbab.ui.admin.product_admin

import androidx.lifecycle.MutableLiveData
import com.commer.app.base.BaseViewModel
import com.fitri.jilbab.data.model.admin.CategoryListResponse
import com.fitri.jilbab.data.model.productAdm.list.ListProductResponse
import com.fitri.jilbab.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductAdminVm @Inject constructor(
    private val productRepository: ProductRepository
) : BaseViewModel() {

    var succesLoad = MutableLiveData<String>()
    var list = MutableLiveData<ListProductResponse>()
    var category = MutableLiveData<CategoryListResponse>()

    suspend fun theList() {
        productRepository.adminProduct(
            onStart = {
            },
            onComplete = {
            },
            onError = {

            },
        ).collect {
            list.postValue(it)
            succesLoad.postValue("200")
        }
    }
    suspend fun categoryList() {
        productRepository.categoryAdmin(
            onStart = {
            },
            onComplete = {
            },
            onError = {

            },
        ).collect {
            category.postValue(it)
            succesLoad.postValue("200")
        }
    }
}