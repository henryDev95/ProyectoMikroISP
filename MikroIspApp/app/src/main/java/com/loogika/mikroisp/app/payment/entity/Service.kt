package com.loogika.mikroisp.app.payment.entity

import android.os.Parcelable
import com.loogika.mikroisp.app.client.service.entity.ServiceDevice
import kotlinx.parcelize.Parcelize
@Parcelize
data class Service(
    var id: Int,
    var name: String?,
    var status:Int,
    var latitude:Float?,
    var longitude:Float?,
    var plan:Plan,
    var description:String?,
    var address:String?,
    var serviceDevices:List<ServiceDevice>
):Parcelable
@Parcelize
data class Plan (
    var id: Int,
    var name:String,
    var downloadSpeed:Int,
    var uploadSpeed:Int,
    var value:Float,
    var status:String,
    var valueServiceReactivation:Int,
    var valueServiceInstallation:Int,
    var fullValue:Float
):Parcelable
