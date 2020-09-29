package com.yobuligo.demoretrofitandroid.services

import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import retrofit2.Call

class ServiceExecutor : IServiceExecutor {

    override suspend fun <T : Any> execute(call: Call<T>): T {
        lateinit var result: T

        //Although the "execute" method is already called synchronously
        //It must be executed on the IO-Thread
        GlobalScope.async(Dispatchers.IO) {
            val response = call.execute()
            if (!response.isSuccessful()) {
                throw WebServiceCallException()
            }

            result = response.body() ?: throw WebServiceCallException()
        }.await()

        return result
    }

}