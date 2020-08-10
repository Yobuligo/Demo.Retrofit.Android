package com.yobuligo.demoretrofitandroid.services

import com.yobuligo.demoretrofitandroid.Config
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceFactory : IServiceFactory {
    private lateinit var networkConnection: Retrofit

    override fun <T> createService(service: Class<T>): T {
        return getNetworkConnection().create(service)
    }

    private fun getNetworkConnection(): Retrofit {
        if (!this::networkConnection.isInitialized) {
            networkConnection = Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        return networkConnection
    }
}