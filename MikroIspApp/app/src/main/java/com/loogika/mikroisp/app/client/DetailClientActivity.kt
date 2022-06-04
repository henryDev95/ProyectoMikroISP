package com.loogika.mikroisp.app.client

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.loogika.mikroisp.app.databinding.ActivityDetailClientBinding



class DetailClientActivity :AppCompatActivity(){

    private lateinit var binding:ActivityDetailClientBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Vincula la variabl con la vista
        binding = ActivityDetailClientBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

}