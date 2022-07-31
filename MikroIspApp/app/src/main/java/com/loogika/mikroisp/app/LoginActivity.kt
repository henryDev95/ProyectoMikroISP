package com.loogika.mikroisp.app

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.content.Intent
import android.util.Log
import android.view.Gravity

import com.loogika.mikroisp.app.databinding.ActivityLoginBinding // sirve para vincular la vista de la actividad
import com.loogika.mikroisp.app.user.User
import com.loogika.mikroisp.app.user.UserProvider
import com.loogika.mikroisp.app.user.ValidarCampos
import com.loogika.mikroisp.app.user.serviceApi.ApiService
import com.loogika.mikroisp.app.user.serviceApi.RetrofilServiceUser
import com.loogika.mikroisp.app.user.serviceApi.UserPost
import com.loogika.mikroisp.app.user.serviceApi.UserResponse
import com.loogika.mikroisp.app.user.toast.ImprimirResultado
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    var correo: String? = null
    var password: String? = null

    lateinit var validarDatos: ValidarCampos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.login.setOnClickListener {
             // validate()

           var intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)

        }
    }

    private fun validate() {
        correo = binding.email.editText?.text.toString()
        password = binding.password.editText?.text.toString()
        validarDatos = ValidarCampos(correo.toString(), password.toString())
        val result =
            arrayOf(validarDatos.validateEmail(binding), validarDatos.validatePassword(binding))
        if (false in result) {
            return
        }
        val user = createObjetc()
        obtenerDatosUsers(user)
    }

    fun obtenerDatosUsers(user:UserPost) {
        val call = RetrofilServiceUser.retrofitUser().create(ApiService::class.java)
        call.authentificationUser(user).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.body() != null) {
                    val User = response.body()!!.entities
                    if(User != null && User.rol[1].id == 9){
                         finish()
                         //ImprimirResultado.suceessResulLogin(this@LoginActivity)
                         val internt = Intent(this@LoginActivity, DashboardActivity::class.java)
                         startActivity(internt)

                    }else{
                         ImprimirResultado.warningResulLogin(this@LoginActivity)
                        borrarCampos()
                        return
                    }
                } else {
                    borrarCampos()
                    return
                }
            }
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                borrarCampos()
                return
            }
        })
    }

    fun createObjetc(): UserPost {
        return UserPost(
            binding.email.editText?.text.toString(),
            binding.password.editText?.text.toString()
        )
    }

    fun borrarCampos(){
        binding.email.editText?.setText("")
        binding.password.editText?.setText("")
    }

}
