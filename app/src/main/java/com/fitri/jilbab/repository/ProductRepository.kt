package com.fitri.jilbab.repository

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
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val apiService: ApiServices,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun listUnpaid(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit,
    ) = flow {
        val response = apiService.getUnpaid()
        response.suspendOnSuccess {
            emit(data)
        }.onError {
            onError(this.message())
        }.onException {
            onError(this.message())
        }
    }
        .onStart { onStart() }
        .onCompletion { onComplete() }
        .flowOn(ioDispatcher)

    suspend fun listIncom(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit,
    ) = flow {
        val response = apiService.getIncoming()
        response.suspendOnSuccess {
            emit(data)
        }.onError {
            onError(this.message())
        }.onException {
            onError(this.message())
        }
    }
        .onStart { onStart() }
        .onCompletion { onComplete() }
        .flowOn(ioDispatcher)

}