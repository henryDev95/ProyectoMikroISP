package com.loogika.mikroisp.app.device.entity

import com.google.gson.annotations.SerializedName

data class DeviceResponse(
    @SerializedName("totalRows") var totalDevice:Int,
    @SerializedName("entities") var devices:List<Device>
)
