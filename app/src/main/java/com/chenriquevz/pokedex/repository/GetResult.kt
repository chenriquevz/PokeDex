package com.chenriquevz.pokedex.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.chenriquevz.pokedex.api.Result
import kotlinx.coroutines.Dispatchers
import retrofit2.Response

object GetResult {

    fun <T, A> resultProvideCallSaveLiveData(
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
            } else if (responseStatus.status == Result.Status.ERROR && source.value?.data == null) {
                emit(
                    Result.error<T>(
                        responseStatus.message!!
                    )
                )
                emitSource(source)
            }
        }

    suspend fun <A> resultCallSaveIncrementPage(
        networkCall: suspend () -> Result<A>,
        saveCallResult: suspend (A) -> Unit,
        completedCall: (String?) -> Unit
    ) {

        val responseStatus = networkCall.invoke()
        if (responseStatus.status == Result.Status.SUCCESS) {
            saveCallResult(responseStatus.data!!)
            completedCall(responseStatus.message)
        } else if (responseStatus.status == Result.Status.ERROR) {
            completedCall(responseStatus.message!!)
        }
    }

    suspend fun <A> resultCallSave(
        networkCall: suspend () -> Result<A>,
        saveCallResult: suspend (A) -> Unit
    ) {

        val responseStatus = networkCall.invoke()
        if (responseStatus.status == Result.Status.SUCCESS) {
            saveCallResult(responseStatus.data!!)
        }

    }


    fun <T, A> resultPokemonDetail(
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
            } else if (responseStatus.status == Result.Status.ERROR && source.value?.data == null) {
                emit(
                    Result.error<T>(
                        responseStatus.message!!
                    )
                )
                emitSource(source)
            }
        }


suspend fun <A> speciesCallSave(
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


suspend fun <A> varietiesCallSave(
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


suspend fun <T> responseIntoResult(call: suspend () -> Response<T>): Result<T> {
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
    var newMessage = message
    Log.d("error", message)
    when {
        message.contains("404") -> {
            newMessage = "Pokemon not found."
        }
    }

    return Result.error("Network call has failed for a following reason: $newMessage")
}

}