package com.fitri.jilbab.ui.login

import androidx.lifecycle.MutableLiveData
import com.commer.app.base.BaseViewModel
import com.fitri.jilbab.data.local.SharedPref
import com.fitri.jilbab.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: AuthRepository
) : BaseViewModel() {

    var succesData = MutableLiveData<String>()
    var errorLog = MutableLiveData<String>()

    suspend fun login(email: String, password: String) {
        loginRepository.loginAuth(
            onStart = {
                _loading.postValue(true) },

            onComplete = {
                _loading.postValue(false) },
            onError = {
                errorLog.postValue(it)
            },
            email = email,
            password = password
        ).collect {
            succesData.postValue("SUKSES LOGIN")
            SharedPref.isLoggedIn = true
            SharedPref.userToken = it.access_token
            SharedPref.userId = it.data.id_user
            SharedPref.nameUser = it.data.name
            SharedPref.userEmail = it.data.email
            SharedPref.userRole = it.data.role
        }
    }
}