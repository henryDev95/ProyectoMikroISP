package com.loogika.mikroisp.app.client

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.loogika.mikroisp.app.client.ApiService.clientApi
import com.loogika.mikroisp.app.client.adapter.ClientAdapter
import com.loogika.mikroisp.app.client.entity.Client
import com.loogika.mikroisp.app.client.entity.clientResponse
import com.loogika.mikroisp.app.databinding.ActivityClientBinding
import com.loogika.mikroisp.app.interceptor.HeaderInterceptor
import com.loogika.mikroisp.app.payment.entity.Plan
import com.loogika.mikroisp.app.payment.entity.Service
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ClientActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    lateinit var binding:ActivityClientBinding
    private var clients:List<Client> = mutableListOf()
    private var clientsActivos = mutableListOf<Client>()
    private var clientsSuspendidos = mutableListOf<Client>()
    private lateinit var clientAdapter: ClientAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showToolbar()
        mostrarShimmer(this)
        binding.btNewCLient.setOnClickListener {
            var intent = Intent(this,NewClientActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    fun showToolbar(){
        setSupportActionBar(binding.toolbarb)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun mostrarShimmer(context:Context){
        binding.clientsList.layoutManager = LinearLayoutManager(this)
        obtenerDatos(context)
        binding.searchView.setOnQueryTextListener(this)
        Handler(Looper.getMainLooper()).postDelayed({
            binding.viewClientList.isVisible= false
            binding.clientsList.isVisible = true
        }, 2800)
    }

    private fun obtenerDatos(context: Context) { // funcion para obtener los datos del api
        val call = getRetrofit().create(clientApi::class.java)
        call.getAll().enqueue(object : Callback<clientResponse> {
            override fun onResponse(
                call: Call<clientResponse>,
                response: Response<clientResponse>
            ) {
                if(response.body()!= null){
                    clients = response.body()!!.entities // obtener el resultado
                    clients.forEach { client->
                        if(client.services[0].status == 1){
                            clientsActivos.add(client)
                        }
                        if(client.services[0].status == 2){
                            clientsSuspendidos.add(client)
                        }
                    }
                    clientAdapter = ClientAdapter(context,clientsActivos)
                    binding.clientsList.adapter = clientAdapter//enviamos al adaptador el lsitado
                }else{
                    ImprimirRespuesta()
                    Log.d("name", "no hay datos")
                }
            }
            override fun onFailure(call: Call<clientResponse>, t: Throwable) {
                error()
            }
        })
    }

    private fun ImprimirRespuesta() {
        Toast.makeText(this, "No tiene datos ", Toast.LENGTH_SHORT).show()
    }

    private fun getRetrofit(): Retrofit { // funcion de retrofil
        // 34.238.198.216 ---> direccion ip del servidor
        var urlBase = "http://192.168.0.100/proyectos-web/adminwisp/web/app_dev.php/api/v1/client/"
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

    private fun error() { // metodo para informar el error
        Toast.makeText(this, "No tiene informaion", Toast.LENGTH_SHORT).show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        clientAdapter.filter.filter(newText)
        return true
    }
}