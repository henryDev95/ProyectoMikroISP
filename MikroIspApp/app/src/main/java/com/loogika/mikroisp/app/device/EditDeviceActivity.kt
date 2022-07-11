package com.loogika.mikroisp.app.device

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.loogika.mikroisp.app.R
import com.loogika.mikroisp.app.client.ApiService.clientApi
import com.loogika.mikroisp.app.client.entity.ClientPost
import com.loogika.mikroisp.app.databinding.ActivityEditDeviceBinding
import com.loogika.mikroisp.app.device.apiService.RetrofilService
import com.loogika.mikroisp.app.device.apiService.deviceApi
import com.loogika.mikroisp.app.device.entity.Device
import com.loogika.mikroisp.app.device.entity.DeviceEdit
import com.loogika.mikroisp.app.device.toast.ImprimirResultado
import com.loogika.mikroisp.app.interceptor.HeaderInterceptor
import com.loogika.mikroisp.app.payment.entity.Plan
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EditDeviceActivity : AppCompatActivity() {
    lateinit var binding:ActivityEditDeviceBinding
    lateinit var device:Device
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditDeviceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showToolbar()
        obtenerDatos()
        showDataDevice()
        binding.buttCancel.setOnClickListener {
            finish()
        }

        binding.save.setOnClickListener {
            val deviceEdit = crearObjetoDevice()
            mostrarDialog(it.context, deviceEdit, device.id)
        }
    }

    private fun crearObjetoDevice(): DeviceEdit {
        return DeviceEdit(
            binding.name.editText?.text.toString(),
            binding.version.editText?.text.toString(),
            binding.descripcion.editText?.text.toString()
        )
    }

    fun showToolbar(){
        setSupportActionBar(binding.toolbarb)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
    fun obtenerDatos(){
       device = intent.getParcelableExtra<Device>("device")!!
    }

    fun showDataDevice(){
        binding.name.editText?.setText(device.name).toString()
        binding.version.editText?.setText(device.osVersion.toString())
        binding.descripcion.editText?.setText(device.description.toString())
    }


    private fun mostrarDialog(contex: Context, device:DeviceEdit, idDevice:Int) {
        val builder = AlertDialog.Builder(contex)
        builder.setTitle("Editar")
            .setMessage("Deseas editar los datos del equipo?")
            .setPositiveButton(R.string.accept,
                DialogInterface.OnClickListener { dialog, id ->
                    try{
                        guarDatos(device,idDevice)
                        ImprimirResultado.successResultadoEdit(this)
                        volverPanerPrincipal()
                        Log.d("device",device.toString())
                    }catch (e: ArithmeticException){
                        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
                    }

                })
            .setNegativeButton(R.string.cancel,
                DialogInterface.OnClickListener { dialog, id ->
                    ImprimirResultado.cancelarResultadoEdit(this)
                    finish()
                })
        builder.show()
    }


    private fun guarDatos(device: DeviceEdit, id:Int) { // funcion para obtener los datos del api
        CoroutineScope(Dispatchers.IO).launch {
            val call = RetrofilService.getRetrofit().create(deviceApi::class.java).editDevice(device,id)
                .execute()
            val puppies = call.body()
            runOnUiThread {
                if (call.isSuccessful) {
                    Log.d("Exito", puppies.toString())
                } else {
                    Log.d("error cancelado", "solicitud fue abortada")
                }

            }
        }
    }

    fun volverPanerPrincipal(){
        val intent = Intent(this, DeviceActivity::class.java)
        startActivity(intent)
    }


}