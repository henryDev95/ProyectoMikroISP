package com.loogika.mikroisp.app.client.service.entity

data class ServiceEdit(
    var client_id:Int,
    var plan_id:Int,
    var description:String,
    var address:String,
    var latitude:Float,
    var longitude:Float
)
