package com.yobuligo.demoretrofitandroid.services

import retrofit2.Call

interface IServiceExecutor {
    suspend fun <T : Any> execute(call: Call<T>): T
}