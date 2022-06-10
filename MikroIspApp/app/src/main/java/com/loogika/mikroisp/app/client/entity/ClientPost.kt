package com.loogika.mikroisp.app.client.entity

data class ClientPost(
    var type:Int,
    var dni:String,
    var user_first_name:String,
    var user_last_name:String,
    var address:String,
    var city:String,
    var country:String,
    var state:Int,
    var phone1:String,
    var email:String,
    var invoiceMaturityDays:Int,
    var invoiceMaturityDaysOverride:Boolean,
    var stop_service_due:Boolean,
    var stop_service_due_override:Boolean,
    var stop_service_due_day_override:Boolean,
    var description:String
)