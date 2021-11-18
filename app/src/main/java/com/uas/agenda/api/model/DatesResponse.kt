package com.uas.agenda.api.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DatesResponse(
    @Expose
    @SerializedName("body")
    val body: Response
    )

data class Response(
    @Expose
    @SerializedName("response")
    val response: List<Date>
)

data class Date(
    @Expose
    @SerializedName("career")
    val career: String,
    @Expose
    @SerializedName("citedEmployee")
    val citedEmployee:String,
    @Expose
    @SerializedName("date")
    val date:String,
    @Expose
    @SerializedName("email")
    val email:String,
    @Expose
    @SerializedName("firstName")
    val firstName:String,
    @Expose
    @SerializedName("isStudent")
    val isStudent:Boolean,
    @Expose
    @SerializedName("lastName")
    val lastName:String,
    @Expose
    @SerializedName("reasons")
    val reasons:String,
    @Expose
    @SerializedName("registrationNumber")
    val registrationNumber:String,
    @Expose
    @SerializedName("secondLastName")
    val secondLastName:String,
    @Expose
    @SerializedName("status")
    val status:String,
    @Expose
    @SerializedName("timeForDate")
    val timeForDate: String,
    @Expose
    @SerializedName("id")
    val id: String
)
