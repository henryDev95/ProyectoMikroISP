package com.loogika.mikroisp.app.client.validarForm

import com.loogika.mikroisp.app.databinding.ActivityAssignedDeviceBinding

class ValidarFormAssign(user:String, password:String, binding:ActivityAssignedDeviceBinding) {

    var user:String = ""
    var password:String =""
    lateinit var binding:ActivityAssignedDeviceBinding
    init {
        this.user = user
        this.password = password
        this.binding = binding
    }

    fun validarUsuario(): Boolean {
        return if (user.isEmpty()) {
            binding.user.error = "El campo no puede estar vacío"
            false
        } else {
            binding.user.error = null
            true
        }
    }

    fun validarPassword(): Boolean {
        return if (password.isEmpty()) {
            binding.password.error = "El campo no puede estar vacío"
            false
        } else {
            binding.password.error = null
            true
        }
    }
}