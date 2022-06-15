package com.loogika.mikroisp.app.client

import android.os.Bundle
import android.system.ErrnoException
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.loogika.mikroisp.app.R
import com.loogika.mikroisp.app.client.ApiService.clientApi
import com.loogika.mikroisp.app.client.entity.ClientPost
import com.loogika.mikroisp.app.client.validarForm.ValidarForm
import com.loogika.mikroisp.app.databinding.ActivityNewClientBinding
import com.loogika.mikroisp.app.interceptor.HeaderInterceptor
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.util.logging.ErrorManager


class NewClientActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewClientBinding
    private var typeClient: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewClientBinding.inflate(layoutInflater)
        setContentView(binding.root)
      //  setupSpinner()

        binding.save.setOnClickListener {
           // validarCampo()
            try{
                val cliente = crearObjetoClient()
                guarDatos(cliente)
                Log.d("cliente",cliente.toString() )
                FancyToast.makeText(this,"!Se ingreso correctamente!",FancyToast.LENGTH_LONG,FancyToast.SUCCESS, false).show()
            }catch (e: ArithmeticException){
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
            }

        }
        binding.buttCancel.setOnClickListener {
            cancelarResultado()
            finish()
        }
    }


    fun validarCampo() {
        var validar = ValidarForm(
            //binding.dni.text.toString(),
            "dada",
            "daadad",
            "dada",
            "dada",
            "dada",
            "dada",
            "dadada",
            binding
        )
        validar.validarCedula()

    }

    private fun guarDatos(clientePost: ClientPost) { // funcion para obtener los datos del api
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(clientApi::class.java).createClient(clientePost)
                .execute()
            val puppies = call.body()
            runOnUiThread {
                if (call.isSuccessful) {
                     Log.d("Exito", puppies.toString())
                } else {
                    Log.d("error cancelado", "solicitud fue abortada")
                }

            }
        }
    }



    private fun crearObjetoClient(): ClientPost {
        return ClientPost(
            typeClient,
            binding.dni.editText?.text.toString(),
            binding.firstName.editText?.text.toString(),
            binding.lastName.editText?.text.toString(),
            binding.address.editText?.text.toString(),
            "Pujíli",
            "EC",
            1,
            binding.telephone.editText?.text.toString(),
            binding.email.editText?.text.toString(),
            0,
            true,
            true,
            false,
            true,
            binding.descripcion.editText?.text.toString()
        )
    }

    private fun getRetrofit(): Retrofit { // funcion de retrofil
        var urlBase = "http://192.168.0.104/proyectos-web/adminwisp/web/app_dev.php/api/v1/client/"
        return Retrofit.Builder()
            .baseUrl(urlBase)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getInterceptor())
            .build()
    }

    private fun getInterceptor(): OkHttpClient { // para añadir la cabecera en retrofil
        return OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor())
            .build()
    }

    /*
    private fun setupSpinner() { // metodo para obtener el valor de spinner
        val languages = resources.getStringArray(com.loogika.mikroisp.app.R.array.Languages)
        val spinner = binding.editext?.text
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val type = parent.getItemAtPosition(position).toString()
                if (type == "Residencial") {
                    typeClient = 1
                } else {
                    typeClient = 2
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }

     */

    fun cancelarResultado() {
        val toast = FancyToast.makeText(
            this,
            "No se realizo el registro del cliente!",
            FancyToast.LENGTH_LONG,
            FancyToast.WARNING,
            false
        )
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

}