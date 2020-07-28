package com.yobuligo.demoretrofitandroid

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface IPersonDAO {
    @GET("persons")
    fun findAll(): Call<List<Person>>

    @GET("persons/{id}")
    fun findById(@Path("id") id: Long): Call<Person>
}