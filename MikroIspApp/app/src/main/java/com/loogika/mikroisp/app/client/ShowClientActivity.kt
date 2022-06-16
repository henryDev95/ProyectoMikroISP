package com.loogika.mikroisp.app.client

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.loogika.mikroisp.app.R

import com.loogika.mikroisp.app.databinding.ActivityNewClientBinding
import com.loogika.mikroisp.app.databinding.ActivityShowClientBinding
import com.loogika.mikroisp.app.payment.entity.Plan
import com.loogika.mikroisp.app.payment.entity.Service


class ShowClientActivity :AppCompatActivity(){

    private lateinit var binding:ActivityShowClientBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Vincula la variabl con la vista
        binding = ActivityShowClientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var dni = intent.getStringExtra("dni")
        var type = intent.getIntExtra("type",0)
        var userFirstName = intent.getStringExtra("userFirstName")
        var userLastName = intent.getStringExtra("userLastName")
        var address = intent.getStringExtra("address")
        var telephone = intent.getStringExtra("telephone")
        var service = intent.getParcelableExtra<Service>("service")!!
       //   var plan = intent.getParcelableExtra<Plan>("plan")!!
        var city = intent.getStringExtra("city")

        binding.dni.text = dni
        binding.typeClient.text = statusClient(type)
        binding.fistName.text = userFirstName
        binding.lastName.text = userLastName
        binding.direction.text = address
        binding.telephone.text = telephone
        binding.statusClient.text = typeCLient(service.status)
        binding.city.text = city
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