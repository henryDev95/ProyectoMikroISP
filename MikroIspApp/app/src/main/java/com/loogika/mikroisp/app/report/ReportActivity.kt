package com.loogika.mikroisp.app.report

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.loogika.mikroisp.app.databinding.ActivityReportBinding

class ReportActivity : AppCompatActivity() {
    lateinit var binding:ActivityReportBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showToolbar()
    }

    fun showToolbar(){
        setSupportActionBar(binding.toolbarb)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}