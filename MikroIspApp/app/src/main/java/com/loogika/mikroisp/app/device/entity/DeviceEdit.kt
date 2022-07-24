package com.loogika.mikroisp.app.device.entity

data class DeviceEdit(
    var name:String,
    var version:String,
    var description:String,
    var typeDevice: Int,
    var proveedorId:Int,
    var brandId:Int,
    var mac:String,
    var model:String,
    var code:String
)
