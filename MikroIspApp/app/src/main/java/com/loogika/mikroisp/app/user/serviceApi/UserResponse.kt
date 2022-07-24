package com.loogika.mikroisp.app.user.serviceApi

import com.google.gson.annotations.SerializedName
import com.loogika.mikroisp.app.user.User

data class UserResponse(
    @SerializedName("entities") var entities:User,
    @SerializedName("status") var status:String
)
