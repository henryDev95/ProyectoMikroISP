package com.loogika.mikroisp.app.client

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.loogika.mikroisp.app.databinding.ActivityEditClientBinding

class EditClientActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditClientBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditClientBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}