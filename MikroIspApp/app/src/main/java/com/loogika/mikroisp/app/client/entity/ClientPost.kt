package com.loogika.mikroisp.app.client.entity

data class ClientPost(
    var type:Int,
    var dni:String,
    var user_first_name:String,
    var user_last_name:String,
    var address:String,
    var phone1:String,
    var email:String?,
    var description:String?
)