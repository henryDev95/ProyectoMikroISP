package com.loogika.mikroisp.app.client.validarForm

import android.util.Log
import androidx.core.util.PatternsCompat
import com.loogika.mikroisp.app.databinding.ActivityEditClientBinding
import com.loogika.mikroisp.app.databinding.ActivityNewClientBinding
import org.intellij.lang.annotations.Pattern

class ValidarFormFormEdit(
      dni: String,
      firstName: String,
      lastName: String,
      direction: String,
      telephone: String,
      email: String,
      description: String,
      type:String,
      binding:ActivityEditClientBinding
) {
    var dni: String = ""
    var firstName: String = ""
    var lastName: String  = ""
    var direction: String  = ""
    var telephone: String  = ""
    var email: String  = ""
    var description: String = ""
    var type:String=""
    lateinit var binding: ActivityEditClientBinding

    init{
        this.dni = dni
        this.firstName = firstName
        this.lastName = lastName
        this.direction = direction
        this.telephone =telephone
        this.email=email
        this.description=description
        this.type = type
        this.binding = binding
    }

    fun validarCedula():Boolean {
        if (dni.isEmpty()) {
            binding.dni.error = "El campo no puede estar vacio"
            return false
        } else {

            if(dni.length != 10){
                binding.dni.error = "Solo se permite 10 dígitos"
                return false
            }else{

                var valor = 0
                var suma = 0
                var cedula = IntArray(10)
                for (i in 0..9) {
                    cedula[i] = Integer.parseInt(dni[i].toString())
                }
                var identificador = cedula[9]
                Log.d("iddentificador", identificador.toString())
                for (i in 0..8) {
                    if (i % 2 == 0) {
                        valor = cedula[i] * 2
                    } else {
                        valor = cedula[i] * 1
                    }
                    if (valor >= 10) {
                        valor = valor - 9

                    }
                    suma += valor
                }

                var ValorDecena = (10 - (suma % 10) % 10)
               var cedulaEs = false
                if (ValorDecena == identificador) {
                    binding.dni.error = null
                    cedulaEs = true
                } else {
                    binding.dni.error = "cédula Incorrecta"
                }
                return cedulaEs
            }
        }
    }


    fun validarFistName(): Boolean {
        return if (firstName.isEmpty()) {
            binding.firstName.error = "El campo no puede estar vacío"
            false
        } else {
            binding.firstName.error = null
            true
        }
    }

    fun validarLastName(): Boolean {
        return if (lastName.isEmpty()) {
            binding.lastName.error = "El campo no puede estar vacío"
            false
        } else {
            binding.lastName.error = null
            true
        }
    }

    fun validarDirection(): Boolean {
        return if (direction.isEmpty()) {
            binding.address.error = "El campo no puede estar vacío"
            false
        } else {
            binding.address.error = null
            true
        }
    }

    fun validarTelephone(): Boolean {
        return if (telephone.isEmpty()) {
            binding.telephone.error = "El campo no puede estar vacío"
            false
        } else {
            if(telephone.length!=10){
                binding.telephone.error = "Solo se permite 10 dígitos"
                false
            }else{
                val patert = java.util.regex.Pattern.compile("(09)+[0-9]{8,8}\$")
                if(patert.matcher(telephone).matches()){
                   true
                }else{
                    binding.telephone.error = "Teléfono Incorrecto"
                    false
                }
            }
        }
    }

    fun validarEmail(): Boolean {
        return if (email.isEmpty()) {
            binding.email.error = "El campo no puede estar vacío"
            false
        }else if (!PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.email.error = "Por favor introduzca una dirección de correo electrónico válida"
            false
        }else{
            binding.email.error = null
            true
        }
    }

    fun validarDescription(): Boolean {
        return if (description.isEmpty()) {
            binding.descripcion.error = "El campo no puede estar vacío"
            false
        } else {
            binding.descripcion.error = null
            true
        }
    }

    fun validarType(): Boolean {
        return if (type=="0") {
            binding.autoCompleteText.error = "El campo no puede estar vacío"
            false
        } else {
            binding.autoCompleteText.error = null
            true
        }
    }

}