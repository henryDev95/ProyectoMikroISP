package com.loogika.mikroisp.app.client.service

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.loogika.mikroisp.app.databinding.ActivityListServiceBinding

class ListServiceActivity : AppCompatActivity() {
    lateinit var binding : ActivityListServiceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}