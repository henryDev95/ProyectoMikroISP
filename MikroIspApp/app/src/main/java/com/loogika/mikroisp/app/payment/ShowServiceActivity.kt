package com.loogika.mikroisp.app.payment

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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

        binding.detailIdentification.text = dni.toString()
        binding.detailNames.text= userFirstName.toString()
        binding.detailSurname.text = userLastName.toString()
        binding.detailDirecction.text = address.toString()
        binding.detailTelephone.text = telephone.toString()
        binding.detailPlaName.text = plan.name.toString()
        if(plan.status =="true"){
            binding.detailPlanState.text = "Activo"
        }else{
            binding.detailPlanState.text = "Cancelado"
        }
        binding.detailPlanValue.text = plan.fullValue.toString()
        binding.cobrarButton.setOnClickListener {
            mostrarDialog(it.context)
        }
    }

     private fun mostrarDialog(contex:Context) {
        val builder = AlertDialog.Builder(contex)
        builder.setTitle("Cobros")
            .setMessage("Desea realizar el cobro del servicio de internet?")
            .setPositiveButton("Aceptar",
                DialogInterface.OnClickListener { dialog, id ->
                    Toast.makeText(this, "aceptar", Toast.LENGTH_SHORT).show()
                })
            .setNegativeButton(R.string.cancel,
                DialogInterface.OnClickListener { dialog, id ->
                    Toast.makeText(this, "no aceptar", Toast.LENGTH_SHORT).show()
                })

        builder.show()
    }


}