package com.loogika.mikroisp.app.client

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.loogika.mikroisp.app.client.ApiService.clientApi
import com.loogika.mikroisp.app.client.adapter.ClientAdapter
import com.loogika.mikroisp.app.client.entity.ClientPost
import com.loogika.mikroisp.app.client.entity.clientResponse
import com.loogika.mikroisp.app.databinding.ActivityNewClientBinding
import com.loogika.mikroisp.app.interceptor.HeaderInterceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class NewClientActivity : AppCompatActivity(){

    private lateinit var binding : ActivityNewClientBinding
    private  var typeClient:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Vincula la variable con la vista
        binding = ActivityNewClientBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupSpinner()

        binding.save.setOnClickListener {

            try{

                Toast.makeText(this,binding.firstName.text.toString(), Toast.LENGTH_SHORT).show()
            }catch(e:NumberFormatException){
               Toast.makeText(this, "No se se realizo el ingreso", Toast.LENGTH_SHORT).show()
            }
        }
   }


    private fun guarDatos(clientePost:ClientPost) { // funcion para obtener los datos del api
        val call = getRetrofit().create(clientApi::class.java)
        call.createClient(clientePost).equals(object:Callback<clientResponse>{
            override fun onResponse(
                call: Call<clientResponse>,
                response: Response<clientResponse>
            ) {
                TODO("Not yet implemented")
            }

            override fun onFailure(call: Call<clientResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }


        })

    }




    private fun getRetrofit(): Retrofit { // funcion de retrofil
        var urlBase = "http://192.168.0.108/proyectos-web/adminwisp/web/app_dev.php/api/v1/client/"
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




    private fun setupSpinner() {
        val languages = resources.getStringArray(com.loogika.mikroisp.app.R.array.Languages)
        val spinner = binding.typeclient
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,view: View,position: Int,id: Long) {
                var type = parent.getItemAtPosition(position).toString()
                if(type == "Residencial"){
                    typeClient = 1
                }else{
                    typeClient = 2
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
              // Code to perform some action when nothing is selected
            }
        }
    }
}