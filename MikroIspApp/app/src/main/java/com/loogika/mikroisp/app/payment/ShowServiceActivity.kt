package com.loogika.mikroisp.app.payment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.loogika.mikroisp.app.R
import com.loogika.mikroisp.app.databinding.ActivityShowServiceBinding
import com.loogika.mikroisp.app.payment.entity.Plan

class ShowServiceActivity : AppCompatActivity() {

    lateinit var binding: ActivityShowServiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var dni = intent.getStringExtra("dni")
        var userFirstName = intent.getStringExtra("userFirstName")
        var userLastName = intent.getStringExtra("userLastName")
        var address = intent.getStringExtra("address")
        var country = intent.getStringExtra("country")
        var town = intent.getStringExtra("town")
        var telephone = intent.getStringExtra("telephone")
        var plan  = intent.getParcelableExtra<Plan>("plan")!!

        binding.cobrarButton.setOnClickListener {
            Toast.makeText(this, plan.name.toString(), Toast.LENGTH_SHORT).show()
       }
    }
}