package com.loogika.mikroisp.app.device.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Device(
    var id:Int,
    var code:String,
    var name:String,
    var model:String,
    var mac:String,
    var isAssigned:Boolean,
    var brand:Brand,
    var osVersion:String?,
    var statusDevice:StatusDevice,
    var typeDevice:TypeDevice?,
    var description:String?
): Parcelable

@Parcelize
data class Brand(
    var id: Int,
    var name : String,
    var description : String?,
    var status : Boolean
):Parcelable

@Parcelize
data class StatusDevice(
    var id: Int,
    var name : String,
    var description : String?,
    var status : Boolean
):Parcelable

@Parcelize
data class TypeDevice(
    var id:Int,
    var name:String,
    var description:String?,
    var status : Boolean,
):Parcelable