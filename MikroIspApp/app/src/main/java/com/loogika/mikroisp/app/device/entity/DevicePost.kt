package com.loogika.mikroisp.app.device.entity

data class DevicePost(
    var provideId:Int,
    var brandId:Int,
    var typeDeviceId:Int,
    var statusDeviceId:Int,
    var code:String,
    var name:String,
    var model:String,
    var version:String,
    var mac:String,
    var assigned:Boolean,
    var description:String
)
