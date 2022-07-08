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
import com.loogika.mikroisp.app.device.entity.Brand
import com.loogika.mikroisp.app.device.entity.Device
import com.loogika.mikroisp.app.device.entity.DeviceResponse
import com.loogika.mikroisp.app.device.entity.StatusDevice
import com.loogika.mikroisp.app.interceptor.HeaderInterceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DeviceActivity : AppCompatActivity(),  DeviceAdapter.CellClickListener, SearchView.OnQueryTextListener {
    lateinit var binding:ActivityDeviceBinding
    private var deviceList:List<Device>  = mutableListOf()
    private lateinit var deviceAdapter: DeviceAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeviceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showToolbar()
        showContent(this)
        binding.btNewDevice.setOnClickListener {
            var intent = Intent(this, NewDeviceActivity::class.java)
            startActivity(intent)
        }
    }

    fun showToolbar(){
        setSupportActionBar(binding.toolbarb)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun showContent( context: Context){
        binding.deviceList.layoutManager = LinearLayoutManager(this)
        obtenerDatos(context)
        binding.searchView.setOnQueryTextListener(this)
        Handler(Looper.getMainLooper()).postDelayed({
            binding.viewLoading.isVisible = false
            binding.deviceList.isVisible = true
        }, 2000)
    }

    private fun obtenerDatos(view: Context) { // funcion para obtener los datos del api
        val call = getRetrofit().create(deviceApi::class.java)
        call.getAll().enqueue(object : Callback<DeviceResponse> {
            override fun onResponse(
                call: Call<DeviceResponse>,
                response: Response<DeviceResponse>
            ) {
                if(response.body()!= null){
                    deviceList = response.body()!!.devices // obtener el resultado
                    deviceAdapter = DeviceAdapter( view,deviceList, this@DeviceActivity)
                    binding.deviceList.adapter = deviceAdapter//enviamos al adaptador el lsitado
                }else{
                    ImprimirRespuesta()
                    Log.d("name", "no hay datos")
                }
            }
            override fun onFailure(call: Call<DeviceResponse>, t: Throwable) {
                error()
            }
        })
    }



    private fun getRetrofit(): Retrofit { // funcion de retrofil
        var urlBase = "http://192.168.0.102/proyectos-web/adminwisp/web/app_dev.php/api/v1/device/"
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
        Toast.makeText(this, "No hay datos del equipo", Toast.LENGTH_SHORT).show()
    }

    private fun error() { // metodo para informar el error
        Toast.makeText(this, "No se realizo la llamada", Toast.LENGTH_SHORT).show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return  true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        deviceAdapter.filter.filter(newText)

        if(newText!!.isEmpty()){
            binding.btNewDevice.isVisible = true
        }else{
            binding.btNewDevice.isVisible = false
        }

        return true
    }

    fun mostrarInformacion(){
        val infter = LayoutInflater.from(this) // permite darla unicacion de poder habri
        val viewEdit = infter.inflate(R.layout.edit_device,null)
        val addDialog = AlertDialog.Builder(this)
        addDialog.setView(viewEdit)
            .create()
            .show()
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