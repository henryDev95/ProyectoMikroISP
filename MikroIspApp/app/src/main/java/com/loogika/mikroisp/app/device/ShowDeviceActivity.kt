package com.loogika.mikroisp.app.device

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.loogika.mikroisp.app.databinding.ActivityShowDeviceBinding

class ShowDeviceActivity : AppCompatActivity() {

    lateinit var binding:ActivityShowDeviceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowDeviceBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}