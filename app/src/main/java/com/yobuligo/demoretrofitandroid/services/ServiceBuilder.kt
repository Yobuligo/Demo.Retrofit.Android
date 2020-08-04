package com.yobuligo.demoretrofitandroid.services

import com.yobuligo.demoretrofitandroid.Config
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceBuilder :
    IServiceBuilder {
    override fun <T> build(service: Class<T>): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(Config.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(service)
    }
}