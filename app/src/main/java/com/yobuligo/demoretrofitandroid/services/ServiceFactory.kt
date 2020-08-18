package com.yobuligo.demoretrofitandroid.services

import com.yobuligo.demoretrofitandroid.Config
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ServiceFactory : IServiceFactory {
    private lateinit var networkConnection: Retrofit

    override fun <T> createService(service: Class<T>): T {
        return getNetworkConnection().create(service)
    }

    private fun getNetworkConnection(): Retrofit {
        if (!this::networkConnection.isInitialized) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                .addInterceptor(AuthorizationInterceptor())
                .addInterceptor(loggingInterceptor)
                .build()

            networkConnection = Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        return networkConnection
    }
}