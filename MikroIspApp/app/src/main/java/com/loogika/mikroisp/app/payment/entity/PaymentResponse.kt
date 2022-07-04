package com.loogika.mikroisp.app.payment.entity

import com.google.gson.annotations.SerializedName
import com.loogika.mikroisp.app.client.entity.Client

data class PaymentResponse(
    @SerializedName("totalRows") var totalRows:String,
    @SerializedName("entities") var entities:List<Payment>
)