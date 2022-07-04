package com.loogika.mikroisp.app.client.entity

import com.google.gson.annotations.SerializedName
import com.loogika.mikroisp.app.payment.entity.Service

data class ServiceResponse(
    @SerializedName("entity") var service:Service,
    @SerializedName("status") var status:String
)
