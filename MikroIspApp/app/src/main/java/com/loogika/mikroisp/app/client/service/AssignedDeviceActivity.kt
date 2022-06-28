package com.loogika.mikroisp.app.client.service

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log

import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.loogika.mikroisp.app.client.adapter.ClientAdapter
import com.loogika.mikroisp.app.client.service.adapterDevice.DeviceAdapterAssign

import com.loogika.mikroisp.app.databinding.ActivityAssignedDeviceBinding
import com.loogika.mikroisp.app.device.adapter.DeviceAdapter
import com.loogika.mikroisp.app.device.apiService.deviceApi
import com.loogika.mikroisp.app.device.entity.Device
import com.loogika.mikroisp.app.device.entity.DeviceResponse
import com.loogika.mikroisp.app.interceptor.HeaderInterceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AssignedDeviceActivity : AppCompatActivity(), DeviceAdapterAssign.CellClickListener, SearchView.OnQueryTextListener {
    lateinit var binding: ActivityAssignedDeviceBinding
    private var device: List<Device> = mutableListOf()
    private var deviceAntena = mutableListOf<Device>()
    private var deviceRouter = mutableListOf<Device>()
    lateinit var deviceAdapterAssign:DeviceAdapterAssign

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAssignedDeviceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        obtenerDatos()

        binding.addRouter.setOnClickListener {
            mostrarShimmer(this, 2)
        }

        binding.addAntena.setOnClickListener {
            mostrarShimmer(this, 1)
        }
    }

    private fun mostrarShimmer(context:Context , select:Int){
        binding.items.isVisible = true
        binding.devicesList.layoutManager = LinearLayoutManager(this)

        if(select == 1){
            deviceAdapterAssign = DeviceAdapterAssign(context, deviceAntena, this)
        }else{
            deviceAdapterAssign = DeviceAdapterAssign(context, deviceRouter, this)
        }

        binding.devicesList.adapter = deviceAdapterAssign//enviamos al adaptador el lsitado
        binding.searchView.setOnQueryTextListener(this)
        Handler(Looper.getMainLooper()).postDelayed({
            binding.viewClientList.isVisible= false
            binding.devicesList.isVisible = true
        }, 2800)
    }


    private fun obtenerDatos() { // funcion para obtener los datos del api
        val call = getRetrofit().create(deviceApi::class.java)
        call.getAll().enqueue(object : Callback<DeviceResponse> {
            override fun onResponse(
                call: Call<DeviceResponse>,
                response: Response<DeviceResponse>
            ) {
                if (response.body() != null) {
                    device = response.body()!!.devices // obtener el resultado
                    device.forEach { device->
                        if((device.typeDevice.id == 1) && (!device.isAssigned)){
                            deviceAntena.add(device)
                        }

                        if((device.typeDevice.id == 2) && (!device.isAssigned)){
                            deviceRouter.add(device)
                        }
                    }

                } else {
                    ImprimirRespuesta()

                }
            }

            override fun onFailure(call: Call<DeviceResponse>, t: Throwable) {
                error()
            }
        })
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


    private fun ImprimirRespuesta() {
        Toast.makeText(this, "No hay datos de los equipos ", Toast.LENGTH_SHORT).show()
    }

    private fun error() { // metodo para informar el error
        Toast.makeText(this, "No se realizo la llamada", Toast.LENGTH_SHORT).show()
    }

    override fun onCellClickListener(id: Int,cont:Int) {
       if(cont!= 0){
           binding.devicesList.isEnabled=false
           Toast.makeText(this, "Selccioado", Toast.LENGTH_SHORT).show()
       }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        deviceAdapterAssign.filter.filter(newText)
        return true
    }

}