package com.loogika.mikroisp.app.client.entity

data class Client(
    var id:Int,
    var dni:String,
    var userFirstName:String,
    var userLastName:String,
    var address:String,
    var country:String,
    var city:String,
    var state:State,
    var phone1:String,
    var invoiceMaturityDays:String,
    var invoiceMaturityDaysOverride:String,
 )

data class State(
    var id: Int,
    var country :String,
    var name : String
)
