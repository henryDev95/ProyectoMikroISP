package com.loogika.mikroisp.app.payment

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
import com.loogika.mikroisp.app.client.entity.Client
import com.loogika.mikroisp.app.client.entity.clientResponse
import com.loogika.mikroisp.app.databinding.ActivityPaymentBinding
import com.loogika.mikroisp.app.interceptor.HeaderInterceptor
import com.loogika.mikroisp.app.payment.adapter.PaymentAdapter
import com.loogika.mikroisp.app.payment.apiService.PaymentApi
import com.loogika.mikroisp.app.payment.entity.Payment
import com.loogika.mikroisp.app.payment.entity.PaymentResponse
import com.loogika.mikroisp.app.payment.entity.Plan
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PaymentActivity : AppCompatActivity(), PaymentAdapter.CellClickListener,
    SearchView.OnQueryTextListener {
    lateinit var binding: ActivityPaymentBinding
    private var listClient: List<Payment> = mutableListOf()
    private lateinit var paymentAdapter: PaymentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showToolbar()
        mostrarShimmer()
    }

    fun showToolbar() {
        setSupportActionBar(binding.toolbarb)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun mostrarShimmer() {
        binding.clientsList.layoutManager = LinearLayoutManager(this)
        obtenerDatos()
        binding.searchView.setOnQueryTextListener(this)
        Handler(Looper.getMainLooper()).postDelayed({
            binding.viewClientList.isVisible = false
            binding.clientsList.isVisible = true
        }, 3000)
    }

    private fun obtenerDatos() { // funcion para obtener los datos del api
        val call = getRetrofit().create(PaymentApi::class.java)
        call.getAll().enqueue(object : Callback<PaymentResponse> {
            override fun onResponse(
                call: Call<PaymentResponse>,
                response: Response<PaymentResponse>
            ) {
                if (response.body() != null) {
                    listClient = response.body()!!.entities // obtener el resultado
                    paymentAdapter = PaymentAdapter(listClient, this@PaymentActivity)
                    binding.clientsList.adapter = paymentAdapter//enviamos al adaptador el lsitado
                } else {
                    //ImprimirRespuesta()
                    Log.d("name", "no hay datos")
                }
            }

            override fun onFailure(call: Call<PaymentResponse>, t: Throwable) {
                error()
            }
        })
    }

    private fun getRetrofit(): Retrofit { // funcion de retrofil
        var urlBase = "http://192.168.0.100/proyectos-web/adminwisp/web/app_dev.php/api/v1/invoice/"
        return Retrofit.Builder()
            .baseUrl(urlBase)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getInterceptor())
            .build()
    }

    private fun error() { // metodo para informar el error
        Toast.makeText(this, "No tiene informaion", Toast.LENGTH_SHORT).show()
    }

    private fun getInterceptor(): OkHttpClient { // para añadir la cabecera en retrofil
        return OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor())
            .build()
    }

    override fun onCellClickListener(
        idInvoice: Int,
        numberInvoice: String,
        total: Float,
        id: Int,
        type: Int,
        dni: String,
        userFirstName: String,
        userLastName: String,
        address: String,
        telephone: String,
        plan: Plan
    ) {

        var intent = Intent(this, ShowServiceActivity::class.java)
        intent.putExtra("idInvoice", idInvoice)
        intent.putExtra("numberInvoice", numberInvoice)
        intent.putExtra("total", total)
        intent.putExtra("id", id)
        intent.putExtra("type", type)
        intent.putExtra("dni", dni)
        intent.putExtra("userFirstName", userFirstName)
        intent.putExtra("userLastName", userLastName)
        intent.putExtra("address", address)
        intent.putExtra("telephone", telephone)
        intent.putExtra("plan", plan)
        startActivity(intent)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        paymentAdapter.filter.filter(newText)
        return true
    }

}