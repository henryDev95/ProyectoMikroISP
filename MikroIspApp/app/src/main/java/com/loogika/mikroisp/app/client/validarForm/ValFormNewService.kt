package com.loogika.mikroisp.app.client.validarForm

import com.loogika.mikroisp.app.databinding.ActivityServiceClientBinding

class ValFormNewService(plan:String, description:String, direcction:String, latitud:String, longitud:String, binding:ActivityServiceClientBinding) {
    var plan:String = ""
    var description:String=""
    var direcction:String=""
    var latitud:String=""
    var longitud:String=""
    lateinit var binding:ActivityServiceClientBinding
    init {
        this.plan=plan
        this.description=description
        this.direcction=direcction
        this.latitud=latitud
        this.longitud=longitud
        this.binding = binding
    }

    fun validarPlan(): Boolean {
        return if (plan=="0") {
            binding.autoCompletePlan.error = "El campo no puede estar vacío"
            false
        } else {
            binding.autoCompletePlan.error = null
            true
        }
    }

    fun validarDescription(): Boolean {
        return if (description.isEmpty()) {
            binding.description.error = "El campo no puede estar vacío"
            false
        } else {
            binding.description.error = null
            true
        }
    }

    fun validarDirection(): Boolean {
        return if (direcction.isEmpty()) {
            binding.address.error = "El campo no puede estar vacío"
            false
        } else {
            binding.address.error = null
            true
        }
    }

    fun validarLatitud(): Boolean {
        return if (latitud.isEmpty()) {
            binding.latitud.error = "El campo no puede estar vacío"
            false
        } else {
            binding.latitud.error = null
            true
        }
    }

    fun validarLongitud(): Boolean {
        return if (longitud.isEmpty()) {
            binding.longuitud.error = "El campo no puede estar vacío"
            false
        } else {
            binding.longuitud.error = null
            true
        }
    }





}