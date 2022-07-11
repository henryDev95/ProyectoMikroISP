package com.loogika.mikroisp.app.device.validation

import com.loogika.mikroisp.app.databinding.ActivityNewDeviceBinding

class ValidarCampo(
    name: String,
    code: String,
    model: String,
    mac: String,
    version: String,
    descrption: String,
    provider: String,
    brand:String,
    type:String,
    binding: ActivityNewDeviceBinding
) {

    var name: String = ""
    var code: String = ""
    var model: String = ""
    var mac: String = ""
    var version: String = ""
    var descrption: String = ""
    var provider: String = ""
    var brand:String = ""
    var type:String=""
    lateinit var binding: ActivityNewDeviceBinding

    init {
        this.name = name
        this.code = code
        this.model = model
        this.mac = mac
        this.version = version
        this.descrption = descrption
        this.provider = provider
        this.brand = brand
        this.type=type
        this.binding = binding
    }

    fun validarName(): Boolean {
        return if (name.isEmpty()) {
            binding.name.error = "El campo no puede estar vacío"
            false
        } else {
            binding.name.error = null
            true
        }
    }

    fun validarCode(): Boolean {
        return if (code.isEmpty()) {
            binding.code.error = "El campo no puede estar vacío"
            false
        } else {
            binding.code.error = null
            true
        }
    }

    fun validarModel(): Boolean {
        return if (model.isEmpty()) {
            binding.model.error = "El campo no puede estar vacío"
            false
        } else {
            binding.model.error = null
            true
        }
    }

    fun validarMac(): Boolean {
        return if (mac.isEmpty()) {
            binding.mac.error = "El campo no puede estar vacío"
            false
        } else {
            binding.mac.error = null
            true
        }
    }

    fun validarVersion(): Boolean {
        return if (version.isEmpty()) {
            binding.version.error = "El campo no puede estar vacío"
            false
        } else {
            binding.version.error = null
            true
        }
    }

    fun validarDescription(): Boolean {
        return if (descrption.isEmpty()) {
            binding.descripcion.error = "El campo no puede estar vacío"
            false
        } else {
            binding.descripcion.error = null
            true
        }
    }

    fun validarProvider(): Boolean {
        return if (provider == "0") {
            binding.autoCompleteProvider.error = "El campo no puede estar vacío"
            false
        } else {
            binding.autoCompleteProvider.error = null
            true
        }
    }

    fun validarBrand(): Boolean {
        return if (brand == "0") {
            binding.autoCompleteBrand.error = "El campo no puede estar vacío"
            false
        } else {
            binding.autoCompleteBrand.error = null
            true
        }
    }

    fun validarType(): Boolean {
        return if (type == "0") {
            binding.autoCompleteText.error = "El campo no puede estar vacío"
            false
        } else {
            binding.autoCompleteText.error = null
            true
        }
    }
}