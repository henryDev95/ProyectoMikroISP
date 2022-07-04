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
import com.loogika.mikroisp.app.device.apiService.deviceApi
import com.loogika.mikroisp.app.device.entity.Device
import com.loogika.mikroisp.app.device.entity.DeviceEdit
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
                        successResultado()
                        volverPanerPrincipal()
                        Log.d("device",device.toString())
                    }catch (e: ArithmeticException){
                        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
                    }

                })
            .setNegativeButton(R.string.cancel,
                DialogInterface.OnClickListener { dialog, id ->
                    cancelarResultado()
                    finish()
                })
        builder.show()
    }


    private fun guarDatos(device: DeviceEdit, id:Int) { // funcion para obtener los datos del api
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(deviceApi::class.java).editDevice(device,id)
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


    private fun getRetrofit(): Retrofit { // funcion de retrofil
        var urlBase = "http://192.168.0.101/proyectos-web/adminwisp/web/app_dev.php/api/v1/device/"
        return Retrofit.Builder()
            .baseUrl(urlBase)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getInterceptor())
            .build()
    }

    private fun getInterceptor(): OkHttpClient { // para añadir la cabecera en retrofil
        return OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor())
            .build()
    }

    fun cancelarResultado() {
        val toast = FancyToast.makeText(
            this,
            "No se edito los datos del equipo!",
            FancyToast.LENGTH_LONG,
            FancyToast.WARNING,
            false
        )
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    fun successResultado() {
        val toast = FancyToast.makeText(
            this,
            "Se edito los datos del equipo correctamente!",
            FancyToast.LENGTH_LONG,
            FancyToast.SUCCESS,
            false
        )
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    fun volverPanerPrincipal(){
        val intent = Intent(this, DeviceActivity::class.java)
        startActivity(intent)
    }


}