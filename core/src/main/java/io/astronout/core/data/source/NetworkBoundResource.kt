package io.astronout.core.data.source

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.StatusCode
import com.skydoves.sandwich.message
import io.astronout.core.vo.Resource
import kotlinx.coroutines.flow.*
import timber.log.Timber

abstract class NetworkBoundResource<ResultType : Any, RequestType> {

    private var result: Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading)
        val dbSource = loadFromDB().first()
        if (shouldFetch(dbSource)) {
            emit(Resource.Loading)
            when (val apiResponse = createCall().first()) {
                is ApiResponse.Success -> {
                    saveCallResult(apiResponse.data)
                    emitAll(loadFromDB().map {
                        Resource.Success(it)
                    })
                }
                is ApiResponse.Failure.Error -> {
                    onFetchFailed()
                    Timber.d(apiResponse.message())
                    val error = when (apiResponse.statusCode) {
                        StatusCode.InternalServerError -> "InternalServerError"
                        StatusCode.BadGateway -> "BadGateway"
                        else -> "${apiResponse.statusCode}(${apiResponse.statusCode.code}): ${apiResponse.message()}"
                    }
                    emit(
                        Resource.Error(error)
                    )
                }
                is ApiResponse.Failure.Exception -> {
                    emit(Resource.Error("Oops, something went wrong!"))
                }
            }
        } else {
            emitAll(loadFromDB().map {
                Resource.Success(it)
            })
        }
    }

    protected open fun onFetchFailed() {}

    protected abstract fun loadFromDB(): Flow<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>

    protected abstract suspend fun saveCallResult(data: RequestType)

    fun asFlow(): Flow<Resource<ResultType>> = result
}