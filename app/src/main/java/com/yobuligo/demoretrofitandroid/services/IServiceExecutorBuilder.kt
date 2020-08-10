package com.yobuligo.demoretrofitandroid.services

import retrofit2.Call

interface IServiceExecutorBuilder<T> {
    fun setCall(call: Call<T>): IServiceExecutorBuilder<T>
    suspend fun execute(call: Call<T>): IServiceExecutorBuilder<T>
    fun setResponseHandler(responseHandler: IResponseHandler<T>): IServiceExecutorBuilder<T>
}