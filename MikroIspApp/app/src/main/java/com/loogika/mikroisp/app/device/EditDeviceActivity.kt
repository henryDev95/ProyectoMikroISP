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
    var type: Int = 0
    var providerDevice: Int = 0
    var brandType: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditDeviceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showToolbar()
        obtenerDatos()
        showDataDevice()
        ObtenerDatosSpinnerType()
        ObtenerDatosSpinnerProvider()
        ObtenerDatosSpinnerBrand()
        binding.buttCancel.setOnClickListener {
            ImprimirResultado.cancelarResultadoEdit(this)
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
            binding.descripcion.editText?.text.toString(),
            type,
            providerDevice,
            brandType,
            binding.mac.editText?.text.toString(),
            binding.model.editText?.text.toString(),
            binding.code.editText?.text.toString(),
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
        binding.code.editText?.setText(device.code).toString()
        binding.mac.editText?.setText(device.mac).toString()
        binding.autoCompleteProvider.setText(device.provider?.name.toString())

        if(device.provider != null  || device.typeDevice != null ||device.brand != null ){
            providerDevice = device.provider!!.id
            type = device.typeDevice!!.id
            brandType = device.brand!!.id
        }

        binding.autoCompleteText.setText(device.typeDevice?.name.toString())
        binding.autoCompleteBrand.setText(device.brand?.name.toString())
        binding.version.editText?.setText(device.osVersion.toString())
        binding.descripcion.editText?.setText(device.description.toString())
        binding.model.editText?.setText(device.model).toString()
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
                        finish()
                        volverPanerPrincipal()

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
                    return@runOnUiThread
                }

            }
        }
    }

    fun volverPanerPrincipal(){
        val intent = Intent(this, DeviceActivity::class.java)
        startActivity(intent)
    }


    fun ObtenerDatosSpinnerProvider() {
        val provider = resources.getStringArray(R.array.provider)
        val adapter = ArrayAdapter(this, R.layout.items_spinner, provider)
        binding.autoCompleteProvider.setAdapter(adapter)
        binding.autoCompleteProvider.setOnItemClickListener { AdapterView, view, i, l ->
            val providerDev = AdapterView.getItemAtPosition(i).toString()
            when (providerDev) {
                "ZC Mayoristas" -> {
                    providerDevice = 1
                    true
                }
                "TecnoMega C.A" -> {
                    providerDevice = 2
                    true
                }
                "INTCOMEX" -> {
                    providerDevice = 8
                    true
                }
                "PLUS COMPU" -> {
                    providerDevice = 7
                    true
                }
                "Lanbowan" -> {
                    providerDevice = 9
                    true
                }
                else -> {
                    providerDevice = 0
                }
            }
        }
    }

    fun ObtenerDatosSpinnerType() {
        val tipoDevice = resources.getStringArray(R.array.typeDevice)
        val adapter = ArrayAdapter(this, R.layout.items_spinner, tipoDevice)
        binding.autoCompleteText.setAdapter(adapter)
        binding.autoCompleteText.setOnItemClickListener { AdapterView, view, i, l ->
            val typeDevice = AdapterView.getItemAtPosition(i).toString()
            when (typeDevice) {
                "Radio-Antena" -> {
                    type = 1
                    true
                }
                "Router" -> {
                    type = 2
                    true
                }
                else -> {
                    type = 0
                }

            }
        }
    }

    fun ObtenerDatosSpinnerBrand() {
        val brand = resources.getStringArray(R.array.brand)
        val adapter = ArrayAdapter(this, R.layout.items_spinner, brand)
        binding.autoCompleteBrand.setAdapter(adapter)
        binding.autoCompleteBrand.setOnItemClickListener { AdapterView, view, i, l ->
            val brandDevice = AdapterView.getItemAtPosition(i).toString()
            when (brandDevice) {
                "Ubiquiti" -> {
                    brandType = 1
                    true
                }
                "Mikrotik" -> {
                    brandType = 2
                    true
                }
                "DLink" -> {
                    brandType = 4
                    true
                }
                "Cisco" -> {
                    brandType = 6
                    true
                }
                "QPCOM" -> {
                    brandType = 3
                    true
                }
                "TP-LINK" -> {
                    brandType = 3
                    true
                }
                else -> {
                    brandType = 0
                }
            }
        }
    }



}