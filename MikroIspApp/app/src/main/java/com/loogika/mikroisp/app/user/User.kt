package com.loogika.mikroisp.app.user

data class User(
    var name: String,
    var email: String,
    var password: String,
    var rol:List<Rol>
)

data class Rol(
    var id:Int,
    var name:String
)