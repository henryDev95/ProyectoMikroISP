package com.loogika.mikroisp.app.client

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.loogika.mikroisp.app.client.service.ServiceClientActivity
import com.loogika.mikroisp.app.databinding.ActivityDetailClientBinding
import com.loogika.mikroisp.app.payment.entity.Plan


class DetailClientActivity :AppCompatActivity(){

    private lateinit var binding:ActivityDetailClientBinding
    var idClient:Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailClientBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recuperarDatos()
        binding.addService.setOnClickListener {
            finish()
            val intent = Intent(this,ServiceClientActivity::class.java)
            intent.putExtra("id",idClient)
            intent.putExtra("mostrar","no")
            startActivity(intent)
        }

    }

    fun recuperarDatos(){
        idClient = intent.getIntExtra("id",0)
        var type = intent.getIntExtra("type",0)
        var dni = intent.getStringExtra("dni")
        var userFirstName = intent.getStringExtra("userFirstName")
        var userLastName = intent.getStringExtra("userLastName")
        var address = intent.getStringExtra("address")
        var telephone = intent.getStringExtra("telephone")
        var email = intent.getStringExtra("email")

        binding.idClient.text = idClient.toString()
        binding.dni.text = dni
        binding.fistName.text = userFirstName
        binding.lastName.text = userLastName
        if(type==1){
            binding.typeClient.text="Residencial"
        }else{
            binding.typeClient.text = "Empresarial"
        }
        binding.direction.text = address
        binding.telephone.text = telephone
        binding.email.text = email
    }

}