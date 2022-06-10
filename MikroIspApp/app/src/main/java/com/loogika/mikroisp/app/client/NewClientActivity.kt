package com.loogika.mikroisp.app.client

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.loogika.mikroisp.app.client.ApiService.clientApi
import com.loogika.mikroisp.app.client.entity.ClientPost
import com.loogika.mikroisp.app.databinding.ActivityNewClientBinding
import com.loogika.mikroisp.app.interceptor.HeaderInterceptor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory


class NewClientActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewClientBinding
    private var typeClient: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewClientBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupSpinner()

        binding.save.setOnClickListener {
            val cliente = crearObjetoClient()
            guarDatos(cliente)
        }
    }


    private fun guarDatos(clientePost: ClientPost) { // funcion para obtener los datos del api
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(clientApi::class.java).createClient(clientePost)
                .execute()
            val puppies = call.body()
            runOnUiThread {
                if (call.isSuccessful) {
                    imprimirResultado()
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
            binding.dni.text.toString(),
            binding.firstName.text.toString(),
            binding.lastName.text.toString(),
            binding.address.text.toString(),
            "Pujíli",
            "EC",
            1,
            binding.telephone.text.toString(),
            binding.Email.text.toString(),
            0,
            true,
            true,
            false,
            true,
            binding.descripcion.text.toString()
        )
    }


    fun imprimirResultado(){
        val toast = Toast.makeText(this, "!Se ingreso correctamente el cliente! ", Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER,0,0)
        toast.show()
    }
    private fun getRetrofit(): Retrofit { // funcion de retrofil
        var urlBase = "http://192.168.0.106/proyectos-web/adminwisp/web/app_dev.php/api/v1/client/"
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

    private fun setupSpinner() { // metodo para obtener el valor de spinner
        val languages = resources.getStringArray(com.loogika.mikroisp.app.R.array.Languages)
        val spinner = binding.typeclient
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
                // Code to perform some action when nothing is selected
            }
        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.viewRoot.windowToken, 0)
    }
}