package com.uas.agenda.api.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DeclineData(
    @Expose
    @SerializedName("id")
    val id: String?
)
