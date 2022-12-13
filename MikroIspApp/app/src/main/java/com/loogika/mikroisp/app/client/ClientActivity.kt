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
import com.loogika.mikroisp.app.client.ApiService.RetrofitService
import com.loogika.mikroisp.app.client.ApiService.clientApi
import com.loogika.mikroisp.app.client.adapter.ClientAdapter
import com.loogika.mikroisp.app.client.entity.Client
import com.loogika.mikroisp.app.client.entity.ClientPrueba
import com.loogika.mikroisp.app.client.entity.clientResponse
import com.loogika.mikroisp.app.client.toast.ImprimirResultado
import com.loogika.mikroisp.app.databinding.ActivityClientBinding
import com.loogika.mikroisp.app.interceptor.HeaderInterceptor
import com.loogika.mikroisp.app.payment.entity.Plan
import com.loogika.mikroisp.app.payment.entity.Service
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

     fun mostrarShimmer(context:Context){
        binding.clientsList.layoutManager = LinearLayoutManager(this)
        obtenerDatos(context)
        binding.searchView.setOnQueryTextListener(this)
        Handler(Looper.getMainLooper()).postDelayed({
            binding.viewClientList.isVisible= false
            binding.clientsList.isVisible = true
        }, 2800)
    }

     fun obtenerDatos(context: Context) { // funcion para obtener los datos del api
        val call = RetrofitService.getRetrofitClient().create(clientApi::class.java)
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
                     binding.logoCLients.isVisible = true
                    ImprimirResultado.ImprimirRespuesta(this@ClientActivity)
                     return
                }
            }
            override fun onFailure(call: Call<clientResponse>, t: Throwable) {
                      binding.logoCLients.isVisible = true
                      ImprimirResultado.error(this@ClientActivity)
                      return
            }
        })
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        clientAdapter.filter.filter(newText)
        return true
    }
}