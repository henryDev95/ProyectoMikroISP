package com.loogika.mikroisp.app.payment.entity

import com.loogika.mikroisp.app.client.entity.Client
import java.text.DateFormat
import java.util.*

data class Payment(
    var id:Int,
    var client:Client,
    var number:String,
    var total:Float,
    var createdDate:String
)
