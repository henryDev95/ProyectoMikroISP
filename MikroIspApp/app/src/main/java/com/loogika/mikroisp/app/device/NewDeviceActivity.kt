package com.loogika.mikroisp.app.device

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import com.loogika.mikroisp.app.R
import android.widget.ArrayAdapter
import android.widget.Toast
import com.loogika.mikroisp.app.client.ApiService.clientApi
import com.loogika.mikroisp.app.client.entity.ClientPost
import com.loogika.mikroisp.app.databinding.ActivityNewDeviceBinding
import com.loogika.mikroisp.app.device.apiService.RetrofilService
import com.loogika.mikroisp.app.device.apiService.deviceApi
import com.loogika.mikroisp.app.device.entity.DevicePost
import com.loogika.mikroisp.app.device.toast.ImprimirResulNewDevice
import com.loogika.mikroisp.app.device.validation.ValidarCampo
import com.loogika.mikroisp.app.interceptor.HeaderInterceptor
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception


class NewDeviceActivity : AppCompatActivity() {
    lateinit var binding: ActivityNewDeviceBinding
    var type: Int = 0
    var providerDevice: Int = 0
    var brandType: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewDeviceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showToolbar()
        ObtenerDatosSpinnerProvider()
        ObtenerDatosSpinnerType()
        ObtenerDatosSpinnerBrand()

        binding.save.setOnClickListener {
            validationCampos()
        }
        binding.buttCancel.setOnClickListener {
            ImprimirResulNewDevice.warningNewDevice(this)
            finish()
        }
    }

    fun showToolbar() {
        setSupportActionBar(binding.toolbarb)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }


    fun validationCampos() {
        var name = binding.name.editText?.text.toString()
        var code = binding.code.editText?.text.toString()
        var model = binding.model.editText?.text.toString()
        var mac = binding.mac.editText?.text.toString()
        var version = binding.version.editText?.text.toString()
        var description = binding.descripcion.editText?.text.toString()
        val validarCampos = ValidarCampo(
            name,
            code,
            model,
            mac,
            version,
            description,
            providerDevice.toString(),
            brandType.toString(),
            type.toString(),
            binding
        )
        val result = arrayOf(
            validarCampos.validarName(),
            validarCampos.validarCode(),
            validarCampos.validarModel(),
            validarCampos.validarMac(),
            validarCampos.validarProvider(),
            validarCampos.validarVersion(),
            validarCampos.validarDescription(),
            validarCampos.validarBrand(),
            validarCampos.validarType()
        )
        if (false in result) {
            return
        }

        val device = createDevicePost()
        try{
            guarDatos(device)
            regresarActivityDevice()
        }catch (e:Exception){
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun guarDatos(devicePost: DevicePost) { // funcion para obtener los datos del api
        CoroutineScope(Dispatchers.IO).launch {
            val call =
                RetrofilService.getRetrofit().create(deviceApi::class.java).createDevice(devicePost)
                    .execute()
            val puppies = call.body()
            runOnUiThread {
                if (call.isSuccessful) {
                    if (puppies?.status != "warning") {
                        ImprimirResulNewDevice.successResult(this@NewDeviceActivity)
                    } else {
                        ImprimirResulNewDevice.warningResult(this@NewDeviceActivity)
                    }
                } else {
                    ImprimirResulNewDevice.errorLlamada(this@NewDeviceActivity)
                }
            }
        }
    }

    private fun regresarActivityDevice() {
        val intent= Intent(this, DeviceActivity::class.java)
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

    fun createDevicePost(): DevicePost {
        return DevicePost(
            providerDevice,
            brandType,
            type,
            1,
            binding.code.editText?.text.toString(),
            binding.name.editText?.text.toString(),
            binding.model.editText?.text.toString(),
            binding.version.editText?.text.toString(),
            binding.mac.editText?.text.toString(),
            false,
            binding.descripcion.editText?.text.toString()
        )
    }


}