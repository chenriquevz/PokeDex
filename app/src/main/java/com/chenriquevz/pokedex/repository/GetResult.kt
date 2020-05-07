package com.chenriquevz.pokedex.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.chenriquevz.pokedex.model.PokemonSpecies
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

object GetResult {

    fun <T, A> resultLiveData(
        databaseQuery: () -> LiveData<T>,
        networkCall: suspend () -> Result<A>,
        saveCallResult: suspend (A) -> Unit
    ): LiveData<Result<T>> =
        liveData(Dispatchers.IO) {
            emit(Result.loading<T>())
            val source = databaseQuery.invoke().map {
                Result.success(
                    it
                )
            }
            emitSource(source)

            val responseStatus = networkCall.invoke()
            if (responseStatus.status == Result.Status.SUCCESS) {
                saveCallResult(responseStatus.data!!)
            } else if (responseStatus.status == Result.Status.ERROR) {
                emit(
                    Result.error<T>(
                        responseStatus.message!!
                    )
                )
                emitSource(source)
            }
        }

    fun <T, A> resultGeneralLiveData(
        databaseQuery: () -> LiveData<T>,
        networkCall: suspend () -> Result<A>,
        saveCallResult: suspend (A) -> Unit,
        recursiveAbility: suspend (A) -> Unit,
        recursiveSpecies: suspend (A) -> Unit
    ): LiveData<Result<T>> =
        liveData(Dispatchers.IO) {
            emit(Result.loading<T>())
            val source = databaseQuery.invoke().map {
                Result.success(
                    it
                )
            }
            emitSource(source)

            val responseStatus = networkCall.invoke()
            if (responseStatus.status == Result.Status.SUCCESS) {
                saveCallResult(responseStatus.data!!)
                recursiveAbility(responseStatus.data)
                recursiveSpecies(responseStatus.data)
            } else if (responseStatus.status == Result.Status.ERROR) {
                emit(
                    Result.error<T>(
                        responseStatus.message!!
                    )
                )
                emitSource(source)
            }
        }

    suspend fun <A> loadLiveData(
        networkCall: suspend () -> Result<A>,
        saveCallResult: suspend (A) -> Unit
    ) {

        val responseStatus = networkCall.invoke()
        if (responseStatus.status == Result.Status.SUCCESS) {
            saveCallResult(responseStatus.data!!)
        }

    }

    suspend fun <A> speciesLiveData(
        networkCall: suspend () -> Result<A>,
        saveCallResult: suspend (A) -> Unit,
        recursiveEvolution: suspend (A) -> Unit,
        recursiveVarieties: suspend (A) -> Unit
    ) {

        val responseStatus = networkCall.invoke()
        if (responseStatus.status == Result.Status.SUCCESS) {
            saveCallResult(responseStatus.data!!)
            recursiveEvolution(responseStatus.data)
            recursiveVarieties(responseStatus.data)
        }

    }


    suspend fun <A> resultGeneralVarieties(
        networkCall: suspend () -> Result<A>,
        saveCallResult: suspend (A) -> Unit,
        recursiveAbility: suspend (A) -> Unit
    ) {

        val responseStatus = networkCall.invoke()
        if (responseStatus.status == Result.Status.SUCCESS) {
            saveCallResult(responseStatus.data!!)
            recursiveAbility(responseStatus.data)

        }
    }


    suspend fun <T> getResult(call: suspend () -> Response<T>): Result<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Result.success(
                    body
                )
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(
                e.message ?: e.toString()
            )
        }
    }

    private fun <T> error(message: String): Result<T> {
        return Result.error("Network call has failed for a following reason: $message")
    }

}