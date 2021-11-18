package com.uas.agenda.api.service

import com.uas.agenda.api.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface APIService {

    @POST("/search-dates")
    fun searchDates(): Call<DatesResponse>

    @POST("/sign-in")
    fun signIn(@Body data: LoginData): Call<LoginResponse>

    @POST("/accept-date")
    fun acceptDate(@Body data: AcceptData): Call<LoginResponse>

    @POST("/decline-date")
    fun declineDate(@Body data: DeclineData): Call<LoginResponse>

}