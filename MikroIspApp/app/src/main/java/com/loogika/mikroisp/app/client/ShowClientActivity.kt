package com.loogika.mikroisp.app.client

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.loogika.mikroisp.app.R
import com.loogika.mikroisp.app.client.entity.Client
import com.loogika.mikroisp.app.client.service.ListServiceActivity
import com.loogika.mikroisp.app.client.service.ServiceShowActivity


import com.loogika.mikroisp.app.databinding.ActivityShowClientBinding


class ShowClientActivity :AppCompatActivity(){

    private lateinit var binding:ActivityShowClientBinding
    lateinit var client:Client

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Vincula la variabl con la vista
        binding = ActivityShowClientBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showDateClient()
        showToolbar()
        binding.btnclose.setOnClickListener {
            finish()
        }

        binding.showService.setOnClickListener {
            enviarDatos()
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

    fun showDateClient(){
        client = intent.getParcelableExtra<Client>("client")!!
        binding.dni.text = client.dni
        binding.typeClient.text = statusClient(client.type)
        binding.fistName.text = client.userFirstName.toString()
        binding.lastName.text = client.userLastName.toString()
        binding.direction.text = client.address
        binding.telephone.text = client.phone1
        binding.statusClient.text = typeCLient(client.services[0].status)
        binding.city.text = client.city
    }


    fun typeCLient(type:Int):String{
        if(type == 1 ){
            return "Activo"
        }else{
            return "En conte"
        }
    }

    fun statusClient(type:Int):String{
        if(type == 1 ){
            return "Residencial"
        }else{
            return "Empresarial"
        }
    }

    fun enviarDatos(){
        val intent = Intent(this, ListServiceActivity ::class.java)
        intent.putExtra("client", client)
        startActivity(intent)
    }
}