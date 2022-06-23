package com.loogika.mikroisp.app.device.entity

import com.google.gson.annotations.SerializedName
import com.loogika.mikroisp.app.client.entity.Client

data class DeviceResponse(
    @SerializedName("totalRows") var totalDevice:Int,
    @SerializedName("entities") var devices:List<Device>
)

data class DeviceResp(
    @SerializedName("entity") var device: Device,
    @SerializedName("status") var status:String
)