package com.loogika.mikroisp.app.client

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.loogika.mikroisp.app.databinding.ActivityNewClientBinding

class NewClientActivity : AppCompatActivity(){

    private lateinit var binding : ActivityNewClientBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Vincula la variabl con la vista
        binding = ActivityNewClientBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupSpinner()
        //val string: String = getString(com.loogika.mikroisp.app.R.string.hello)

        binding.save.setOnClickListener {
            val intento1 = Intent(this, DetailClientActivity::class.java)
            startActivity(intento1)
        }


    }

    private fun setupSpinner() {
        val languages = resources.getStringArray(com.loogika.mikroisp.app.R.array.Languages)
        val spinner = binding.typeclient
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
        spinner.adapter = arrayAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,view: View,position: Int,id: Long) {
               // pones obtener la posicion del spinner
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected
            }
        }
    }




}