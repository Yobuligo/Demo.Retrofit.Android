package com.yobuligo.demoretrofitandroid

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestCallRequest : IRestCallRequest {
    override fun <T> create(requestAPI: Class<T>): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(Config.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(requestAPI)
    }
}