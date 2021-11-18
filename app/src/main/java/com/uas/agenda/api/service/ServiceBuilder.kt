package com.uas.agenda.api.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
    private val BASE_URL = "https://api-agenda-uas.herokuapp.com"

    private val builder: Retrofit.Builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())

    private val retrofit = builder.build()


    fun<T> buildService(serviceClass: Class<T>): T {
        return retrofit.create(serviceClass)
    }

}