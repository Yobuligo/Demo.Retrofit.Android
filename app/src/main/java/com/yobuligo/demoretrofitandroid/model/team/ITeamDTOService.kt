package com.yobuligo.demoretrofitandroid.model.team

import com.yobuligo.demoretrofitandroid.model.team.TeamDTO
import retrofit2.Call
import retrofit2.http.*

interface ITeamDTOService {
    @GET("teams")
    fun findAll(): Call<List<TeamDTO>>

    @GET("teams")
    fun findAll(@Query("fields") fields: String): Call<List<TeamDTO>>

    @GET("teams/{id}")
    fun findById(@Path("id") id: Long): Call<TeamDTO>

    @GET("teams/{id}")
    fun findById(@Path("id") id: Long, @Query("fields") fields: String): Call<TeamDTO>

    @POST("teams")
    fun addTeam(@Body team: TeamDTO): Call<TeamDTO>
}