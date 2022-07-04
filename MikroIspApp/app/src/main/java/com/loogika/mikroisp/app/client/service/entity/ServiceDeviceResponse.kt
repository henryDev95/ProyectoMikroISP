package com.loogika.mikroisp.app.client.service.entity

import com.google.gson.annotations.SerializedName

data class ServiceDeviceResponse(
    @SerializedName("entity") var serviceDevice: ServiceDevice,
    @SerializedName("status") var status:String
)
