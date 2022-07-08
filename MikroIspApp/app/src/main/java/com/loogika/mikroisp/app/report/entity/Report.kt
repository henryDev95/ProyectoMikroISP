package com.loogika.mikroisp.app.report.entity

import com.loogika.mikroisp.app.payment.entity.Payment

data class Report(
    var id:Int,
    var invoice:Payment,
    var value:Float,
    var createdDate:String?,
    var createdAt:String?,
    var method:Int
)
