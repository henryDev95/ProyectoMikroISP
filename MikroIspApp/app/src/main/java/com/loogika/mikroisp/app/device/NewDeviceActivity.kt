package com.loogika.mikroisp.app.device

import android.content.Context
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
import com.loogika.mikroisp.app.device.apiService.deviceApi
import com.loogika.mikroisp.app.device.entity.DevicePost
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
    var type:Int = 0
    var providerDevice:Int = 0
    var brandType : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewDeviceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ObtenerDatosSpinnerProvider()
        ObtenerDatosSpinnerType()
        ObtenerDatosSpinnerBrand()

        binding.save.setOnClickListener {
            val device = createDevicePost()
            try{
                 guarDatos(device)
            }catch (e:Exception){
                Toast.makeText(this, e.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }
        binding.buttCancel.setOnClickListener {
           finish()
        }
    }

    private fun guarDatos(devicePost: DevicePost) { // funcion para obtener los datos del api
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(deviceApi::class.java).createDevice(devicePost).execute()
            val puppies = call.body()
            runOnUiThread {
                if (call.isSuccessful) {
                    if(puppies?.status != "warning"){
                       successResult()
                    }else{
                        warningResult()
                    }
                } else {
                    Log.d("error cancelado", "No se realizo la llamada")
                }
            }
        }
    }

    private fun warningResult() {
        FancyToast.makeText(
            this,
            "!Existe un registro con el mismo código!",
            FancyToast.LENGTH_LONG,
            FancyToast.WARNING,
            false
        ).show()
    }

    private fun successResult() {
        FancyToast.makeText(
            this,
            "!Se a ingresado correctamente!",
            FancyToast.LENGTH_LONG,
            FancyToast.SUCCESS,
            false
        ).show()
    }

    private fun getRetrofit(): Retrofit { // funcion de retrofil
        var urlBase = "http://192.168.0.100/proyectos-web/adminwisp/web/app_dev.php/api/v1/device/"
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

    fun ObtenerDatosSpinnerProvider(){
        val provider = resources.getStringArray(R.array.brand)
        val adapter = ArrayAdapter(this, R.layout.items_spinner,provider)
        binding.autoCompleteProvider.setAdapter(adapter)
        binding.autoCompleteProvider.setOnItemClickListener{ AdapterView, view, i,l->
            val providerDev = AdapterView.getItemAtPosition(i).toString()
            when(providerDev){
                "ZC Mayoristas"->{ providerDevice = 1
                    true
                }
                "TecnoMega C.A"->{ providerDevice = 2
                    true
                }
                "INTCOMEX"->{  providerDevice = 8
                    true
                }
                "PLUS COMPU"->{  providerDevice = 7
                    true
                }
                else->{  providerDevice = 9
                }
            }
        }
    }

    fun ObtenerDatosSpinnerType(){
        val tipoDevice = resources.getStringArray(R.array.typeDevice)
        val adapter = ArrayAdapter(this, R.layout.items_spinner,tipoDevice)
        binding.autoCompleteText.setAdapter(adapter)
        binding.autoCompleteText.setOnItemClickListener{ AdapterView, view, i,l->
            val typeDevice = AdapterView.getItemAtPosition(i).toString()
            if(typeDevice=="Radio-Antena"){
                type = 1
            }else{
                type = 2
            }
        }
    }

    fun ObtenerDatosSpinnerBrand(){
        val brand = resources.getStringArray(R.array.brand)
        val adapter = ArrayAdapter(this, R.layout.items_spinner,brand)
        binding.autoCompleteBrand.setAdapter(adapter)
        binding.autoCompleteBrand.setOnItemClickListener{ AdapterView, view, i,l->
            val brandDevice = AdapterView.getItemAtPosition(i).toString()
            when(brandDevice){
                "Ubiquiti"->{ brandType= 1
                    true
                  }
                "Mikrotik"->{ brandType= 2
                    true
                }
                "DLink"->{ brandType= 4
                    true
                }
                "Cisco"->{ brandType= 6
                    true
                }
                "QPCOM"->{ brandType= 3
                    true
                }
                else->{ brandType= 5
                }
            }
        }
    }

    fun createDevicePost():DevicePost{
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