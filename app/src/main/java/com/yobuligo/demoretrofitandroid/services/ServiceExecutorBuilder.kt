package com.yobuligo.demoretrofitandroid.services

import android.content.ContentValues.TAG
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServiceExecutorBuilder<T> : IServiceExecutorBuilder<T> {
    private lateinit var call: Call<T>
    private lateinit var responseHandler: IResponseHandler<T>

    override suspend fun execute(call: Call<T>): IServiceExecutorBuilder<T> {
        executeCall(call)
        return this
    }

    private fun executeCall(call: Call<T>) {
        call.enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                handleFailure(t)
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (!response.isSuccessful) {
                    // TODO: 04.08.2020
                    return
                }

                if (response.body() != null) {
                    responseHandler.onHandleResponse(response.body() as T)
                }
            }
        }
        )
    }

    private fun handleFailure(t: Throwable) {
        Log.e(TAG, "onFailure: Error while calling Service. ${t.message}")
    }

    override fun setCall(call: Call<T>): IServiceExecutorBuilder<T> {
        this.call = call
        return this
    }

    override fun setResponseHandler(responseHandler: IResponseHandler<T>): IServiceExecutorBuilder<T> {
        this.responseHandler = responseHandler
        return this
    }
}