package com.loogika.mikroisp.app.client.service.entity

import android.os.Parcelable
import com.loogika.mikroisp.app.device.entity.Device
import com.loogika.mikroisp.app.payment.entity.Service
import kotlinx.parcelize.Parcelize

@Parcelize
data class ServiceDevice(
    var id:Int,
    var device:Device?,
    var service:Service?,
    var user:String?,
    var password:String?
): Parcelable
