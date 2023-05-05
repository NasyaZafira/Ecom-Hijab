package com.fitri.jilbab.repository

import com.fitri.jilbab.data.model.register.SignUpBody
import com.fitri.jilbab.data.remote.ApiServices
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import timber.log.Timber
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiService: ApiServices,
    private val ioDispatcher: CoroutineDispatcher,
) {
    suspend fun loginAuth(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String) -> Unit,
        email: String,
        password: String
    ) = flow {
        val response = apiService.login(email, password)
        response.suspendOnSuccess {
            emit(this.data)
        }.onError {
            Timber.e(this.message())
            when (raw.networkResponse?.code) {
                400 -> {
                    onError("Email atau Kata Sandi tidak valid")
                }
                401 -> {
                    onError("Email atau Kata Sandi tidak valid")
                }
                404 -> {
                    onError("Server tidak ditemukan")
                }
                500 -> {
                    onError("Server bermasalah, silahkan coba lagi nanti")
                }
                else -> {
                    onError(message())
                }
            }
        }.onException {
            Timber.e(this.message())
            onError(this.message())

        }
    }
        .onStart { onStart() }
        .onCompletion { onComplete() }
        .flowOn(ioDispatcher)

    suspend fun postSignUpAndGetResult(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit,
        body: SignUpBody,
    ) = flow {
        val response = apiService.postSignUp(body)
        response.suspendOnSuccess {
            emit(this.data)
        }.onError {
            Timber.e(this.message())
            onError(this.statusCode.name)
        }.onException {
            Timber.e(this.message)
        }
    }
        .onStart { onStart() }
        .onCompletion { onComplete() }
        .flowOn(ioDispatcher)
}