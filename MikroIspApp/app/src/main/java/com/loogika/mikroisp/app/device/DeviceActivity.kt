package com.loogika.mikroisp.app.device

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.loogika.mikroisp.app.R
import com.loogika.mikroisp.app.databinding.ActivityDeviceBinding
import com.loogika.mikroisp.app.device.adapter.DeviceAdapter
import com.loogika.mikroisp.app.device.apiService.deviceApi
import androidx.core.view.isVisible
import com.loogika.mikroisp.app.client.adapter.ClientAdapter
import com.loogika.mikroisp.app.device.apiService.RetrofilService
import com.loogika.mikroisp.app.device.entity.Brand
import com.loogika.mikroisp.app.device.entity.Device
import com.loogika.mikroisp.app.device.entity.DeviceResponse
import com.loogika.mikroisp.app.device.entity.StatusDevice
import com.loogika.mikroisp.app.device.toast.ImprimirResultado
import com.loogika.mikroisp.app.interceptor.HeaderInterceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DeviceActivity : AppCompatActivity(), DeviceAdapter.CellClickListener,
    SearchView.OnQueryTextListener {
    lateinit var binding: ActivityDeviceBinding
    private var deviceList: List<Device> = mutableListOf()
    private var deviceRouter = mutableListOf<Device>()
    private var deviceAntenas = mutableListOf<Device>()
    var cantidadAntenas: Int = 0
    var cantidadRouter: Int = 0
    private lateinit var deviceAdapter: DeviceAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeviceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showToolbar()
        showContent(this)

        binding.contentAntenas.setOnClickListener {
            binding.titulo.text = "Listado de antenas:"
            mostrarShimmer(this , 1)
        }

        binding.cantidadRouters.setOnClickListener {
            binding.titulo.text = "Listado de routers:"
            mostrarShimmer(this , 2)
        }

        binding.btNewDevice.setOnClickListener {
            finish()
            var intent = Intent(this, NewDeviceActivity::class.java)
            startActivity(intent)
        }
    }



    private fun mostrarShimmer(context:Context , select:Int){

        binding.deviceList.layoutManager = LinearLayoutManager(this)
        if(select == 1){
            deviceAdapter = DeviceAdapter(context, deviceAntenas, this@DeviceActivity)
        }else{
            deviceAdapter = DeviceAdapter(context, deviceRouter, this@DeviceActivity)
        }
        binding.deviceList.adapter = deviceAdapter//enviamos al adaptador el lsitado
        binding.searchView.setOnQueryTextListener(this)
        Handler(Looper.getMainLooper()).postDelayed({
            binding.viewLoading.isVisible= false
            binding.deviceList.isVisible = true
        }, 2800)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    fun showToolbar() {
        setSupportActionBar(binding.toolbarb)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun showContent(context: Context) {
        binding.deviceList.layoutManager = LinearLayoutManager(this)
        obtenerDatos(context)
        binding.searchView.setOnQueryTextListener(this)
        Handler(Looper.getMainLooper()).postDelayed({
            binding.viewLoading.isVisible = false
            binding.deviceList.isVisible = true
        }, 2000)
    }

    private fun obtenerDatos(view: Context) { // funcion para obtener los datos del api
        val call = RetrofilService.getRetrofit().create(deviceApi::class.java)
        call.getAll().enqueue(object : Callback<DeviceResponse> {
            override fun onResponse(
                call: Call<DeviceResponse>,
                response: Response<DeviceResponse>
            ) {
                if (response.body() != null) {
                    deviceList = response.body()!!.devices // obtener el resultado
                    deviceList.forEach { device ->
                        if (device.typeDevice!!.id == 1) {
                            deviceAntenas.add(device)
                            cantidadAntenas++
                        }else{
                            deviceRouter.add(device)
                            cantidadRouter++
                        }
                    }

                    binding.cantidadAntenas.text =  cantidadAntenas.toString()
                    binding.cantidadRouters.text= cantidadRouter.toString()
                    deviceAdapter = DeviceAdapter(view, deviceAntenas, this@DeviceActivity)
                    binding.deviceList.adapter = deviceAdapter//enviamos al adaptador el listado
                } else {

                    binding.logoDevices.isVisible = true
                    ImprimirResultado.ImprimirRespuestoLlamada(this@DeviceActivity)
                    return
                }
            }

            override fun onFailure(call: Call<DeviceResponse>, t: Throwable) {

                binding.logoDevices.isVisible = true
                ImprimirResultado.errorLlamada(this@DeviceActivity)
                return
            }
        })
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        deviceAdapter.filter.filter(newText)

        if (newText!!.isEmpty()) {
            binding.btNewDevice.isVisible = true
        } else {
            binding.btNewDevice.isVisible = false
        }

        return true
    }

    override fun onCellClickListener(
        id: Int,
        name: String,
        code: String,
        model: String,
        mac: String,
        brand: Brand,
        statusDevice: StatusDevice
    ) {
        /*
       var intent = Intent(this.context, ShowServiceActivity::class.java)
       intent.putExtra("dni" ,dni )
       intent.putExtra("userFirstName" ,userFirstName )
       intent.putExtra("userLastName" ,userLastName )
       intent.putExtra("address" ,address)
       intent.putExtra("country" ,country)
       intent.putExtra("town" ,"Pujili")
       intent.putExtra("telephone" ,telephone)
       intent.putExtra("plan", plan)

       startActivity(intent)


        */
    }
}