package com.loogika.mikroisp.app.client

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.loogika.mikroisp.app.R

import com.loogika.mikroisp.app.databinding.ActivityNewClientBinding
import com.loogika.mikroisp.app.databinding.ActivityShowClientBinding


class ShowClientActivity :AppCompatActivity(){

    private lateinit var binding:ActivityShowClientBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Vincula la variabl con la vista
        binding = ActivityShowClientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var dni = intent.getStringExtra("dni")
        var userFirstName = intent.getStringExtra("userFirstName")
        var userLastName = intent.getStringExtra("userLastName")
        var address = intent.getStringExtra("address")
        var country = intent.getStringExtra("country")
        var town = intent.getStringExtra("town")
        var telephone = intent.getStringExtra("telephone")

        binding.detailIdentification.text = dni
        binding.detailNames.text = userFirstName
        binding.detailSurname.text = userLastName
        binding.detailDirecction.text = address
        binding.detailTown.text = town
        binding.detailCountry.text = country
        binding.detailTelephone.text = telephone


    }

}