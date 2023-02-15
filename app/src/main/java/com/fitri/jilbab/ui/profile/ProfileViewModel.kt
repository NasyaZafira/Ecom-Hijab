package com.fitri.jilbab.ui.profile

import androidx.lifecycle.MutableLiveData
import com.commer.app.base.BaseViewModel
import com.fitri.jilbab.data.model.profile.DetailProfileResponse
import com.fitri.jilbab.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository
): BaseViewModel() {
    val userDetail = MutableLiveData<DetailProfileResponse>()

    suspend fun detailProfile() {
        repository.userDetail(
            onStart = {
                _loading.postValue(true) },
            onComplete = {
                _loading.postValue(false) },
            onError = {
                _message.postValue(it) }
        ).collect {
            userDetail.postValue(it)
        }
    }
}