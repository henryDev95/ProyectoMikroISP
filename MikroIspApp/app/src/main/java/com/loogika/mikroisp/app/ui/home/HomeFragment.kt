package com.loogika.mikroisp.app.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.loogika.mikroisp.app.client.ApiService.clientApi
import com.loogika.mikroisp.app.client.ShowClientActivity
import com.loogika.mikroisp.app.client.adapter.ClientAdapter
import com.loogika.mikroisp.app.client.entity.Client
import com.loogika.mikroisp.app.client.entity.clientResponse
import com.loogika.mikroisp.app.databinding.FragmentHomeBinding
import com.loogika.mikroisp.app.interceptor.HeaderInterceptor
import com.loogika.mikroisp.app.payment.entity.Plan
import com.loogika.mikroisp.app.payment.entity.Service
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HomeFragment : Fragment(), SearchView.OnQueryTextListener {
    private lateinit var binding: FragmentHomeBinding
    private var clients: List<Client> = mutableListOf()
    private var clientsActivos = mutableListOf<Client>()
    private var clientsSuspendidos = mutableListOf<Client>()
    private lateinit var clientAdapter: ClientAdapter
    var cantidadActivos:Int = 0
    var cantidadEnCorte:Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        mostrarDatosClientActivos(root.context)
        binding.cantidadClient.setOnClickListener {
            binding.titulo.text = "Listado de clientes activos:"
            mostrarShimmer(root.context , 1)
        }
        binding.cantidadEnCorte.setOnClickListener {
            binding.titulo.text = "Listado de clientes el corte:"
            mostrarShimmer(root.context , 2)
        }
        return root
    }

    private fun mostrarShimmer(context:Context , select:Int){
        binding.items.isVisible = true
        binding.clientsList.layoutManager = LinearLayoutManager(this.context)
        if(select == 1){
            clientAdapter = ClientAdapter(context, clientsActivos)
        }else{
            clientAdapter = ClientAdapter(context, clientsSuspendidos)
        }
        binding.clientsList.adapter = clientAdapter//enviamos al adaptador el lsitado
        binding.searchView.setOnQueryTextListener(this)
        Handler(Looper.getMainLooper()).postDelayed({
            binding.viewClientList.isVisible= false
            binding.clientsList.isVisible = true
        }, 2800)
    }

    private fun mostrarDatosClientActivos(context:Context){
        binding.clientsList.layoutManager = LinearLayoutManager(this.context)
        obtenerDatos(context)
        binding.searchView.setOnQueryTextListener(this)
        Handler(Looper.getMainLooper()).postDelayed({
            binding.viewClientList.isVisible= false
            binding.clientsList.isVisible = true
        }, 2800)
    }

    private fun obtenerDatos( context:Context) { // funcion para obtener los datos del api
        val call = getRetrofit().create(clientApi::class.java)
        call.getAll().enqueue(object : Callback<clientResponse> {
            override fun onResponse(
                call: Call<clientResponse>,
                response: Response<clientResponse>
            ) {
                if (response.body() != null) {
                    clients = response.body()!!.entities // obtener el resultado
                    clients.forEach { client ->
                        if (client.services[0].status == 1) {
                            clientsActivos.add(client)
                            cantidadActivos++
                        }
                        if (client.services[0].status == 2) {
                            clientsSuspendidos.add(client)
                            cantidadEnCorte++
                        }
                    }
                    binding.cantidadClient.text = (clientsActivos.count()).toString()
                    binding.cantidadEnCorte.text = clientsSuspendidos.count().toString()
                    clientAdapter = ClientAdapter(context,clientsActivos)
                    binding.clientsList.adapter = clientAdapter//enviamos al adaptador el lsitado

                } else {
                    ImprimirRespuesta()

                }
            }

            override fun onFailure(call: Call<clientResponse>, t: Throwable) {
                error()
            }
        })
    }

    private fun ImprimirRespuesta() {
        Toast.makeText(this.context, "No tiene datos ", Toast.LENGTH_SHORT).show()
    }

    private fun error() { // metodo para informar el error
        Toast.makeText(this.context, "No tiene informaion", Toast.LENGTH_SHORT).show()
    }

    private fun getRetrofit(): Retrofit { // funcion de retrofil
        // 34.238.198.216 ---> direccion ip del servidor
        var urlBase = "http://192.168.0.105/proyectos-web/adminwisp/web/app_dev.php/api/v1/client/"
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

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        clientAdapter.filter.filter(newText)
        return true
    }


}