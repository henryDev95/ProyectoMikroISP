package com.loogika.mikroisp.app.client.entity

class ClientPost (
    var type:Int,
    var dni:String,
    var user_first_name:String,
    var user_last_name:String,
    var address:String,
    var country:String,
    var city:String,
    var state:Int,
    var phone1:String,
    var stop_service_due:Int,
    var stop_service_due_override:Int,
    var stop_service_due_day_override:Int,
    var invoiceMaturityDays:Int,
    var invoiceMaturityDaysOverride:Int,
    var description:String
)