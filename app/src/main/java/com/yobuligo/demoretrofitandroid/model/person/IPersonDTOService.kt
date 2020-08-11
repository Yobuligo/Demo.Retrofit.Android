package com.yobuligo.demoretrofitandroid.model.person

import com.yobuligo.demoretrofitandroid.model.paging.PageRequestDTO
import retrofit2.Call
import retrofit2.http.*

interface IPersonDTOService {
    @GET("persons")
    fun findAll(): Call<List<PersonDTO>>

    @GET("persons")
    fun findAll(@Query("fields") fields: String): Call<List<PersonDTO>>

    @GET("persons")
    fun findAll(
        @Query("offset") offset: String,
        @Query("limit") limit: String
    ): Call<PageRequestDTO<PersonDTO>>

    @GET("persons/{id}")
    fun findById(@Path("id") id: Long): Call<PersonDTO>

    @GET("persons/{id}")
    fun findById(@Path("id") id: Long, @Query("fields") fields: String): Call<PersonDTO>

    @POST("persons")
    fun addPerson(@Body personDTO: PersonDTO): Call<PersonDTO>
}