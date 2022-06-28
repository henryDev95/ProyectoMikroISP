package com.loogika.mikroisp.app.device.entity

data class Device(
    var id:Int,
    var code:String,
    var name:String,
    var model:String,
    var mac:String,
    var isAssigned:Boolean,
    var brand:Brand,
    var statusDevice:StatusDevice,
    var typeDevice:TypeDevice
)

data class Brand(
    var id: Int,
    var name : String,
    var description : String?,
    var status : Boolean
)

data class StatusDevice(
    var id: Int,
    var name : String,
    var description : String?,
    var status : Boolean
)

data class TypeDevice(
    var id:Int,
    var name:String,
    var description:String?,
    var status : Boolean,
)