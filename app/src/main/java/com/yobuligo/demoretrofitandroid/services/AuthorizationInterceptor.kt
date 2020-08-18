package com.yobuligo.demoretrofitandroid.services

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.util.*

class AuthorizationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val username: String = "test"
        val password: String = "test"
        val base: String = "$username:$password"

        val authorizationHeader: String =
            "Basic " + Base64.getEncoder().encodeToString(base.toByteArray())

        val newRequest = originalRequest.newBuilder()
            .header("Authorization", authorizationHeader)
            .build()
        return chain.proceed(newRequest)
    }
}