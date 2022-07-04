package com.loogika.mikroisp.app.client.service.entity

data class ServiceDevicePost(
    var device_id:Int,
    var service_id:Int,
    var user:String,
    var password:String
)
