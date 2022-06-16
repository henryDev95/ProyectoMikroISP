package com.loogika.mikroisp.app.client



import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager

import com.loogika.mikroisp.app.client.ApiService.clientApi
import com.loogika.mikroisp.app.client.adapter.ClientAdapter
import com.loogika.mikroisp.app.client.entity.Client
import com.loogika.mikroisp.app.client.entity.clientResponse
import com.loogika.mikroisp.app.databinding.FragmentClientBinding
import com.loogika.mikroisp.app.interceptor.HeaderInterceptor
import com.loogika.mikroisp.app.payment.entity.Plan
import com.loogika.mikroisp.app.payment.entity.Service
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
class ClientFragment : Fragment() , ClientAdapter.CellClickListener, SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentClientBinding
    private var clients:List<Client> = mutableListOf()
    private lateinit var clientAdapter: ClientAdapter

     override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

         binding = FragmentClientBinding.inflate(inflater, container, false)
         val root: View = binding.root

         mostrarShimmer()
         binding.btNewCLient.setOnClickListener {
              var intent = Intent(this.context,NewClientActivity::class.java)
              startActivity(intent)
         }
        return root
    }
    private fun mostrarShimmer(){
        binding.clientsList.layoutManager = LinearLayoutManager(this.context)
        obtenerDatos()
        binding.searchView.setOnQueryTextListener(this)
        Handler(Looper.getMainLooper()).postDelayed({
            binding.viewClientList.isVisible= false
            binding.clientsList.isVisible = true
        }, 2800)
    }

    private fun obtenerDatos() { // funcion para obtener los datos del api
        val call = getRetrofit().create(clientApi::class.java)
        call.getAll().enqueue(object : Callback<clientResponse> {
            override fun onResponse(
                call: Call<clientResponse>,
                response: Response<clientResponse>
            ) {
                if(response.body()!= null){
                    clients = response.body()!!.entities // obtener el resultado
                    clientAdapter = ClientAdapter(clients, this@ClientFragment)
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
        Toast.makeText(this.context, "No tiene datos ", Toast.LENGTH_SHORT).show()
    }

    private fun getRetrofit(): Retrofit { // funcion de retrofil
        var urlBase = "http://34.238.198.216/proyectos-web/adminwisp/web/app_dev.php/api/v1/client/"
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
        Toast.makeText(this.context, "No tiene informaion", Toast.LENGTH_SHORT).show()
    }

    override fun onCellClickListener(
        id:Int,
        type:Int,
        dni: String,
        userFirstName: String,
        userLastName: String,
        address: String,
        telephone:String,
        service: Service,
        plan:Plan,
        city:String
    ) {
        var intent = Intent(this.context, ShowClientActivity::class.java)
        intent.putExtra("id",id)
        intent.putExtra("type",type)
        intent.putExtra("dni" ,dni )
        intent.putExtra("userFirstName" ,userFirstName )
        intent.putExtra("userLastName" ,userLastName )
        intent.putExtra("address" ,address)
        intent.putExtra("telephone" ,telephone)
        intent.putExtra("service", service)
        intent.putExtra("plan", plan)
        intent.putExtra("city" ,city)
        startActivity(intent)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
      clientAdapter.filter.filter(newText)
        return true
    }
}