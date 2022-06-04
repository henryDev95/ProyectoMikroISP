package com.loogika.mikroisp.app.payment


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.loogika.mikroisp.app.client.adapter.ClientAdapter
import com.loogika.mikroisp.app.client.entity.Client
import com.loogika.mikroisp.app.databinding.FragmentPaymentBinding
import com.loogika.mikroisp.app.payment.adapter.apiService.apiPayment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PaymentFragment : Fragment() ,  ClientAdapter.CellClickListener, SearchView.OnQueryTextListener  {

    lateinit var  binding:FragmentPaymentBinding
    private var clients = mutableListOf<Client>()
    private lateinit var clientAdapter: ClientAdapter

     override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       binding = FragmentPaymentBinding.inflate(inflater, container, false)
       val root: View = binding.root
       binding.searchView.setOnQueryTextListener(this)
         initRecycleView()
        return root
    }

    private fun initRecycleView() {
        clientAdapter = ClientAdapter(clients, this)
        binding.clientsList.layoutManager = LinearLayoutManager(this.context)
        binding.clientsList.adapter = clientAdapter

    }



    private fun getRetrofit(): Retrofit { // funcion de retrofil
        var urlBase = "http://192.168.0.104/proyectos-web/adminwisp/web/app_dev.php/api/v1/client/"
        return Retrofit.Builder()
            .baseUrl(urlBase)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


     private fun searchByName( name : String){
         CoroutineScope(Dispatchers.IO).launch {
             val token = "abcdefg1234567890"
             val call  = getRetrofit().create(apiPayment::class.java).getClientByName(token, "$name/retriveByName?institution_id=1")
             val client = call.body()
             activity?.runOnUiThread {
                 if(call.isSuccessful){
                    val clientByName = client?.entities ?: emptyList()
                     if(!clientByName.isEmpty()){
                         clients.clear()
                         clients.addAll(clientByName)
                         clientAdapter.notifyDataSetChanged()
                     }else{
                         ImprimirRespuesta()
                     }


                 }else{
                     error()
                 }
               //   hideKeyboard()
             }
         }

     }

    private fun ImprimirRespuesta() {
        Toast.makeText(this.context, "No hay datos del cliente con ese nombre ", Toast.LENGTH_SHORT).show()
    }

    private fun error() { // metodo para informar el error
        Toast.makeText(this.context, "No se realizo la llamada", Toast.LENGTH_SHORT).show()
    }

    override fun onCellClickListener(
        dni: String,
        userFirstName: String,
        userLastName: String,
        address: String,
        country: String,
        telephone: String
    ) {
        TODO("Not yet implemented")
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(!query.isNullOrEmpty()){
            searchByName(query)
        }
        return  true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    private fun hideKeyboard() {
        val imm = ClassLoader.getSystemResource(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.viewRoot.windowToken, 0)
    }



}


