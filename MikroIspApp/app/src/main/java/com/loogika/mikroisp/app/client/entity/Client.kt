package com.loogika.mikroisp.app.client.entity

import android.os.Parcelable
import com.loogika.mikroisp.app.payment.entity.Service
import kotlinx.parcelize.Parcelize

@Parcelize
data class Client(
    var id:Int,
    var type:Int,
    var dni:String,
    var userFirstName:String?,
    var userLastName:String?,
    var address:String?,
    var country:String?,
    var city:String?,
    var state:State?,
    var phone1:String?,
    var email:String?,
    var description:String?,
    var invoiceMaturityDays:String?,
    var invoiceMaturityDaysOverride:Boolean?,
    var services:List<Service>
 ): Parcelable
@Parcelize
data class State(
    var id: Int,
    var country :String?,
    var name : String?
):Parcelable
