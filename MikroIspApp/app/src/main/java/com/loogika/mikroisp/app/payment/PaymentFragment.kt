package com.loogika.mikroisp.app.payment


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.loogika.mikroisp.app.client.ShowClientActivity
import com.loogika.mikroisp.app.client.adapter.ClientAdapter
import com.loogika.mikroisp.app.client.entity.Client
import com.loogika.mikroisp.app.databinding.FragmentPaymentBinding
import com.loogika.mikroisp.app.interceptor.HeaderInterceptor
import com.loogika.mikroisp.app.payment.adapter.PaymentAdapter
import com.loogika.mikroisp.app.payment.adapter.apiService.apiPayment
import com.loogika.mikroisp.app.payment.entity.Plan
import com.loogika.mikroisp.app.payment.entity.ServiceClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PaymentFragment : Fragment() ,  PaymentAdapter.CellClickListener, SearchView.OnQueryTextListener  {

    lateinit var  binding:FragmentPaymentBinding
    private var clientService  = mutableListOf<ServiceClient>()
    private lateinit var paymentAdapter: PaymentAdapter

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
        paymentAdapter = PaymentAdapter(clientService, this)
        binding.clientsList.layoutManager = LinearLayoutManager(this.context)
        binding.clientsList.adapter = paymentAdapter

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

    private fun searchByName( name : String){
         CoroutineScope(Dispatchers.IO).launch {
             val call  = getRetrofit().create(apiPayment::class.java).getClientByName( "$name/retriveByName?institution_id=1")
             val client = call.body()
             activity?.runOnUiThread {
                 if(call.isSuccessful){
                    val clientByName = client?.entities ?: emptyList()
                     if(!clientByName.isEmpty()){
                         clientService.clear()
                         clientService.addAll(clientByName)
                         paymentAdapter.notifyDataSetChanged()
                     }else{
                         ImprimirRespuesta()
                     }
                 }else{
                     error()
                 }
             }
         }

     }

    private fun ImprimirRespuesta() {
        Toast.makeText(this.context, "No hay datos del cliente con ese nombre ", Toast.LENGTH_SHORT).show()
    }

    private fun error() { // metodo para informar el error
        Toast.makeText(this.context, "No se realizo la llamada", Toast.LENGTH_SHORT).show()
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


    override fun onCellClickListener(
        dni: String,
        userFirstName: String,
        userLastName: String,
        address: String,
        country: String,
        telephone: String,
        plan: Plan
    ) {

        var intent = Intent(this.context, ShowServiceActivity::class.java)
        intent.putExtra("dni" ,dni )
        intent.putExtra("userFirstName" ,userFirstName )
        intent.putExtra("userLastName" ,userLastName )
        intent.putExtra("address" ,address)
        intent.putExtra("country" ,country)
        intent.putExtra("town" ,"Pujili")
        intent.putExtra("telephone" ,telephone)
        intent.putExtra("plan", plan)

        startActivity(intent)
    }


}


