package com.loogika.mikroisp.app.client.entity

data class ServicePost(
    var plan_id: Int,
    var state_id:Int,
    var client_id:Int,
    var tax_id:Int,
    var description:String,
    var address:String,
    var latitude:Float,
    var longitude:Float
)
