package com.loogika.mikroisp.app.client


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.Toast

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager

import com.loogika.mikroisp.app.client.ApiService.clientApi
import com.loogika.mikroisp.app.client.adapter.ClientAdapter
import com.loogika.mikroisp.app.client.entity.Client
import com.loogika.mikroisp.app.client.entity.clientResponse
import com.loogika.mikroisp.app.databinding.FragmentClientBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
class ClientFragment : Fragment() , ClientAdapter.CellClickListener {

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

         initRecycleView()
         binding.btNewCLient.setOnClickListener {
              var intent = Intent(this.context,NewClientActivity::class.java)
              startActivity(intent)

         }
         return root
    }

    private fun initRecycleView() {
        binding.clientsList.layoutManager = LinearLayoutManager(this.context)
            obtenerDatos()
    }

    private fun obtenerDatos() { // funcion para obtener los datos del api
        //val token = "abcdefg1234567890"
        val token = "123456henry"
        val call = getRetrofit().create(clientApi::class.java)
        call.getAll(token).enqueue(object : Callback<clientResponse> {
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
        var urlBase = "http://192.168.0.108/proyectos-web/adminwisp/web/app_dev.php/api/v1/client/"
        return Retrofit.Builder()
            .baseUrl(urlBase)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun error() { // metodo para informar el error
        Toast.makeText(this.context, "No tiene informaion", Toast.LENGTH_SHORT).show()
    }

    override fun onCellClickListener(
        dni: String,
        userFirstName: String,
        userLastName: String,
        address: String,
        country: String,
        telephone:String
    ) {
        var intent = Intent(this.context, ShowClientActivity::class.java)
        intent.putExtra("dni" ,dni )
        intent.putExtra("userFirstName" ,userFirstName )
        intent.putExtra("userLastName" ,userLastName )
        intent.putExtra("address" ,address)
        intent.putExtra("country" ,country)
        intent.putExtra("town" ,"Pujili")
        intent.putExtra("telephone" ,telephone)
        startActivity(intent)
    }


}