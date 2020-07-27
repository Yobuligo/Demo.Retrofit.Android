package com.yobuligo.demoretrofitandroid

import retrofit2.Call
import retrofit2.http.GET

interface IMindSetAPI {
    @GET("mindsets")
    fun getMindsets(): Call<List<MindSet>>
}