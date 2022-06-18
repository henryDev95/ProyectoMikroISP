package com.loogika.mikroisp.app.device

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.loogika.mikroisp.app.R
import android.widget.ArrayAdapter
import android.widget.Toast
import com.loogika.mikroisp.app.databinding.ActivityNewDeviceBinding


class NewDeviceActivity : AppCompatActivity(), AdapterView.OnItemClickListener {
    lateinit var binding: ActivityNewDeviceBinding
    var type = ""
    var provide = ""
    var brand = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewDeviceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ObtenerDatosSpinnerProvider()
        ObtenerDatosSpinnerType()
        ObtenerDatosSpinnerBrand()

        binding.buttCancel.setOnClickListener {
            Toast.makeText(this, type+provide+brand, Toast.LENGTH_SHORT).show()
        }

        Toast.makeText(this, type+provide+brand, Toast.LENGTH_SHORT).show()
    }

    fun ObtenerDatosSpinnerProvider(){
        val provider = resources.getStringArray(R.array.brand)
        val adapter = ArrayAdapter(this, R.layout.items_spinner,provider)
        with(binding.autoCompleteProvider){
            setAdapter(adapter)
            onItemClickListener = this@NewDeviceActivity
        }
    }


    fun ObtenerDatosSpinnerType(){
        val tipoDevice = resources.getStringArray(R.array.typeDevice)
        val adapter = ArrayAdapter(this, R.layout.items_spinner,tipoDevice)
        with(binding.autoCompleteText){
            setAdapter(adapter)
            onItemClickListener = this@NewDeviceActivity
        }
    }

    fun ObtenerDatosSpinnerBrand(){
        val brand = resources.getStringArray(R.array.brand)
        val adapter = ArrayAdapter(this, R.layout.items_spinner,brand)
        with(binding.autoCompleteBrand){
            setAdapter(adapter)
            onItemClickListener = this@NewDeviceActivity
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        type = parent?.getItemAtPosition(position).toString()
      //  provide = parent?.getItemAtPosition(position).toString()
       // brand = parent?.getItemAtPosition(position).toString()

    }
}