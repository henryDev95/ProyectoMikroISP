package com.loogika.mikroisp.app.client

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.loogika.mikroisp.app.databinding.ActivityDetailClientBinding
import com.loogika.mikroisp.app.payment.entity.Plan


class DetailClientActivity :AppCompatActivity(){

    private lateinit var binding:ActivityDetailClientBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailClientBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var type = intent.getIntExtra("type",0)
        var dni = intent.getStringExtra("dni")
        var userFirstName = intent.getStringExtra("userFirstName")
        var userLastName = intent.getStringExtra("userLastName")
        var address = intent.getStringExtra("address")
        var telephone = intent.getStringExtra("telephone")
        var email = intent.getStringExtra("email")

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