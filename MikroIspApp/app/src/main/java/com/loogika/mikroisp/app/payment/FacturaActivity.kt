package com.loogika.mikroisp.app.payment

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.pdf.PdfDocument
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.loogika.mikroisp.app.databinding.ActivityFacturaBinding

class FacturaActivity : AppCompatActivity() {
    lateinit var binding:ActivityFacturaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFacturaBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}