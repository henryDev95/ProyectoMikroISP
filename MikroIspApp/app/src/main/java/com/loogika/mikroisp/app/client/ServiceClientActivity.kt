package com.loogika.mikroisp.app.client

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


import com.loogika.mikroisp.app.databinding.ActivityNewServiceBinding


class ServiceClientActivity :AppCompatActivity(){

    private lateinit var binding:ActivityNewServiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Vincula la variabl con la vista
        binding = ActivityNewServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*

        binding.save.setOnClickListener {

            val intento1 = Intent(this, DetailClientActivity::class.java)
            startActivity(intento1)
        }

     */
    }

}