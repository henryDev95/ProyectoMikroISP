package com.loogika.mikroisp.app.client.service

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.loogika.mikroisp.app.databinding.ActivityServiceClientBinding

class ServiceClientActivity : AppCompatActivity() {
    lateinit var binding:ActivityServiceClientBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceClientBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}