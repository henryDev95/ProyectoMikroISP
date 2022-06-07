package com.loogika.mikroisp.app.payment

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.loogika.mikroisp.app.R
import com.loogika.mikroisp.app.databinding.ActivityShowServiceBinding
import com.loogika.mikroisp.app.payment.entity.Plan

class ShowServiceActivity : AppCompatActivity() {

    lateinit var binding: ActivityShowServiceBinding
    var catidadPagos:Int = 0

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

        binding.cancelarButton.setOnClickListener {
            finish()
        }
    }

     private fun mostrarDialog(contex:Context) {
        val builder = AlertDialog.Builder(contex)
        builder.setTitle("Cobros")
            .setMessage("Desea realizar el cobro del servicio de internet?")
            .setPositiveButton("Aceptar",
                DialogInterface.OnClickListener { dialog, id ->
                    catidadPagos++
                    var valor = DatosValor().setValor(catidadPagos)
                    val toast = Toast.makeText(this, "Se realizó el cobro $valor", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.CENTER,0,0)
                    toast.show()
                    finish()
                })
            .setNegativeButton(R.string.cancel,
                DialogInterface.OnClickListener { dialog, id ->

                Toast.makeText(this, "No se realizó el cobro", Toast.LENGTH_SHORT).show()
                    finish()
                })

        builder.show()
    }

    fun imprimirResultado(){
        catidadPagos += catidadPagos
        val toast = Toast.makeText(this, "Se realizó el cobro $catidadPagos", Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER,0,0)
        toast.show()

    }

}