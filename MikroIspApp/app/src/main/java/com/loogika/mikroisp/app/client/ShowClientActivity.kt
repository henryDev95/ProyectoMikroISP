package com.loogika.mikroisp.app.client

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.loogika.mikroisp.app.R
import com.loogika.mikroisp.app.client.entity.Client


import com.loogika.mikroisp.app.databinding.ActivityShowClientBinding


class ShowClientActivity :AppCompatActivity(){

    private lateinit var binding:ActivityShowClientBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Vincula la variabl con la vista
        binding = ActivityShowClientBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val client = intent.getParcelableExtra<Client>("client")!!
        binding.dni.text = client.dni
        binding.typeClient.text = statusClient(client.type)
        binding.fistName.text = client.userFirstName.toString()
        binding.lastName.text = client.userLastName.toString()
        binding.direction.text = client.address
        binding.telephone.text = client.phone1
        binding.statusClient.text = typeCLient(client.services[0].status)
        binding.city.text = client.city
        binding.btnclose.setOnClickListener {
            finish()
        }
    }


    fun typeCLient(type:Int):String{
        if(type == 1 ){
            return "Activo"
        }else{
            return "Cancelado"
        }
    }

    fun statusClient(type:Int):String{
        if(type == 1 ){
            return "Residencial"
        }else{
            return "Empresarial"
        }
    }
}