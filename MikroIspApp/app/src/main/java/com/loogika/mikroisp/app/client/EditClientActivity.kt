package com.loogika.mikroisp.app.client

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import com.loogika.mikroisp.app.client.entity.Client
import com.loogika.mikroisp.app.databinding.ActivityEditClientBinding
import com.shashank.sony.fancytoastlib.FancyToast

class EditClientActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditClientBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditClientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val client = intent.getParcelableExtra<Client>("client")!!




        binding.buttCancel.setOnClickListener {
            cancelarResultado()
            finish()
        }
    }


    fun cancelarResultado() {
        val toast = FancyToast.makeText(
            this,
            "No se realizo el registro del cliente!",
            FancyToast.LENGTH_LONG,
            FancyToast.WARNING,
            false
        )
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }
}