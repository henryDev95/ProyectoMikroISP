package com.loogika.mikroisp.app.client.service

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.loogika.mikroisp.app.client.entity.Client
import com.loogika.mikroisp.app.client.service.adapterService.ServiceAdapter
import com.loogika.mikroisp.app.databinding.ActivityListServiceEditBinding

class ListServiceEditActivity : AppCompatActivity() , ServiceAdapter.CellClickListener {
    lateinit var binding:ActivityListServiceEditBinding
    lateinit var client: Client
    lateinit var clientService: Client
    var Position:Int = 0;
    private lateinit var serviceAdapter: ServiceAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListServiceEditBinding.inflate(layoutInflater)
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

    override fun onCellClickListener(id: Int, position: Int) {

        client.services.forEach {
            if(id == it.id){
                clientService = client
            }
        }
        Position = position;


        enviarDatos()
    }
    fun  enviarDatos(){
        val intent = Intent(this, EditServiceActivity::class.java)
        intent.putExtra("client", clientService)
        intent.putExtra("position",Position)
        startActivity(intent)

    }

}