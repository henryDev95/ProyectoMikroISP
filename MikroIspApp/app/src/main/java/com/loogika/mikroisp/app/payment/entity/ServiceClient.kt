package com.loogika.mikroisp.app.payment.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class ServiceClient(
    var dni:String,
    var userFirstName:String,
    var userLastName:String,
    var address:String,
    var country:String,
    var phone1:String,
    var services:List<Service>
)

data class Service(
    var id: Int,
    var name: String,
    var status:Int,
    var latitude:Float,
    var longitude:Float,
    var plan:Plan
)
@Parcelize
data class Plan (
    var id: Int,
    var name:String,
    var downloadSpeed:Int,
    var uploadSpeed:Int,
    var status:String,
    var valueServiceReactivation:Int,
    var valueServiceInstallation:Int,
    var fullValue:Float
):Parcelable
