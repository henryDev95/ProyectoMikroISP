package com.loogika.mikroisp.app.client.service

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.loogika.mikroisp.app.client.adapter.ClientAdapter
import com.loogika.mikroisp.app.client.entity.Client
import com.loogika.mikroisp.app.client.service.adapterService.ServiceAdapter
import com.loogika.mikroisp.app.client.service.entity.ServiceDevice
import com.loogika.mikroisp.app.databinding.ActivityListServiceBinding
import com.loogika.mikroisp.app.payment.entity.Plan
import com.loogika.mikroisp.app.payment.entity.Service

class ListServiceActivity : AppCompatActivity() , ServiceAdapter.CellClickListener{
    lateinit var binding : ActivityListServiceBinding
    lateinit var client: Client
    lateinit var clientService:Client
    private lateinit var serviceAdapter: ServiceAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showToolbar()
        showDateService()
        showInformationServiceList()
    }

    fun showInformationServiceList(){
        binding.servicesList.layoutManager = LinearLayoutManager(this)
        serviceAdapter = ServiceAdapter(this,client.services!!,this)
        binding.servicesList.adapter = serviceAdapter
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    fun showToolbar(){
        setSupportActionBar(binding.toolbarb)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun showDateService(){
       client = intent.getParcelableExtra<Client>("client")!!
    }

    override fun onCellClickListener(id: Int) {

        Toast.makeText(this, "id--->"+ id.toString(), Toast.LENGTH_SHORT).show()
        client.services.forEach {
            if(id == it.id){
                clientService = client
            }
        }
        enviarDatos()
    }
    fun  enviarDatos(){
         val intent = Intent(this, ServiceShowActivity::class.java)
         intent.putExtra("client", clientService)
         startActivity(intent)

    }


}