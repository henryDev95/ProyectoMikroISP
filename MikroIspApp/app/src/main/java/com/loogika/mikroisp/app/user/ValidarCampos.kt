package com.loogika.mikroisp.app.user

import androidx.core.util.PatternsCompat
import com.loogika.mikroisp.app.databinding.ActivityLoginBinding


class ValidarCampos (Email: String, Password: String, users: List<User>) {

    var Email: String = ""
    var Password: String = ""
    var users: List<User>
    init {
        this.Email = Email
        this.Password = Password
        this.users=users

    }

     fun validatePassword(binding: ActivityLoginBinding) : Boolean { // metodo para validar la contraseña
         return if (Password.isEmpty()){
            binding.password.error = "El campo no puede estar vacío"
            false
        }else{
            binding.password.error = null
            true
        }
    }

    fun validateEmail(binding: ActivityLoginBinding) : Boolean { // metodo para validar el imail
        return if (Email.isEmpty()) {
            binding.email.error = "El campo no puede estar vacío"
            false
        }else if (!PatternsCompat.EMAIL_ADDRESS.matcher(Email).matches()) {
            binding.email.error = "Por favor introduzca una dirección de correo electrónico válida"
            false
        }else{
            binding.email.error = null
            true
        }
    }

   fun VerificarUserContraseña():Boolean {
        var encontrado = false
        users.forEach {
            if((it.email == Email) && (it.password == Password)){
                encontrado = true
            }
        }
        return encontrado
    }

}