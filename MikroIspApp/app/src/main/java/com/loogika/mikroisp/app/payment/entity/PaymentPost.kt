package com.loogika.mikroisp.app.payment.entity

data class PaymentPost(
    var invoice_id:Int,
    var value:Float,
    var description:String?
)
