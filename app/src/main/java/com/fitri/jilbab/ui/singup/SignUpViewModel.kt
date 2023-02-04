package com.fitri.jilbab.ui.singup

import androidx.lifecycle.MutableLiveData
import com.commer.app.base.BaseViewModel
import com.fitri.jilbab.data.model.register.SignUpBody
import com.fitri.jilbab.data.model.register.SignUpResponse
import com.fitri.jilbab.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val mainRepository: AuthRepository
) : BaseViewModel() {

    val signUpResponse = MutableLiveData<SignUpResponse>()
    var errorLog = MutableLiveData<String>()


    suspend fun signUp(
        email: String,
        name: String,
        password: String,
        role: String
    ) {
        val body = SignUpBody(email, name, password, role)
        mainRepository.postSignUpAndGetResult(
            onStart = {
                _loading.postValue(true) },
            onComplete = {
                _loading.postValue(false) },
            onError = {
                _message.postValue(it) },
            body
        ).collect {
            signUpResponse.postValue(it)
        }
    }

}