package com.yobuligo.demoretrofitandroid

import retrofit2.Call
import retrofit2.http.GET

interface IPersonDAO {
    @GET("persons")
    fun getPersons(): Call<List<Person>>
}