package com.fitri.jilbab.ui.profile

import androidx.lifecycle.MutableLiveData
import com.commer.app.base.BaseViewModel
import com.fitri.jilbab.data.model.profile.DetailProfileResponse
import com.fitri.jilbab.data.model.profile.edit.EditProfileBody
import com.fitri.jilbab.data.model.profile.edit.EditProfileResponse
import com.fitri.jilbab.data.model.profile.password.BodyPassword
import com.fitri.jilbab.data.model.profile.password.ChangePassResponse
import com.fitri.jilbab.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository
) : BaseViewModel() {
    val userDetail = MutableLiveData<DetailProfileResponse>()
    val updateProfile = MutableLiveData<EditProfileResponse>()
    val changePass = MutableLiveData<ChangePassResponse>()

    suspend fun detailProfile() {
        repository.userDetail(
            onStart = {
                _loading.postValue(true)
            },
            onComplete = {
                _loading.postValue(false)
            },
            onError = {
                _message.postValue(it)
            }
        ).collect {
            userDetail.postValue(it)
        }
    }

    suspend fun editProfile(
        address: String,
        date_of_birth: String,
        gender: String,
        name: String,
        phone: String
    ) {
        val body = EditProfileBody(address, date_of_birth, gender, name, phone)
        repository.userUpdate(
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
            updateProfile.postValue(it)
        }
    }

    suspend fun updatePass(
        confirmpassword: String,
        currentpassword: String,
        newpassword: String
    ) {
        val body = BodyPassword(confirmpassword, currentpassword, newpassword)
        repository.passUpdate(
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
            changePass.postValue(it)
        }
    }
}