package com.loogika.mikroisp.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.content.Intent
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.core.util.PatternsCompat

import com.loogika.mikroisp.app.databinding.ActivityLoginBinding // sirve para vincular la vista de la actividad
import com.loogika.mikroisp.app.user.User
import com.loogika.mikroisp.app.user.UserProvider
import com.loogika.mikroisp.app.user.ValidarCampos
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    var correo:String? = null
    var password:String? = null
    var Users:List<User> = listOf()
    lateinit var validarDatos:ValidarCampos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Users = UserProvider.getProdutos()
        binding.login.setOnClickListener {
//             validate()
            val internt = Intent(this, DashboardActivity::class.java)
            startActivity(internt)

          }

    }

    private fun validate(){
        correo = binding.email.editText?.text.toString()
        password = binding.password.editText?.text.toString()
        validarDatos = ValidarCampos(correo.toString() ,password.toString(),Users)
        val result = arrayOf(validarDatos.validateEmail(binding), validarDatos.validatePassword(binding))
        if (false in result){
            return
        }
        if(validarDatos.VerificarUserContraseña()){
            val intento1 = Intent(this, DashboardActivity::class.java)
            startActivity(intento1)
        }else{
            Toast.makeText(this, "! Correo y Contraseña Incorrecta !", Toast.LENGTH_SHORT).show()
            binding.email.editText?.setText("")
            binding.password.editText?.setText("")
        }
    }
}
