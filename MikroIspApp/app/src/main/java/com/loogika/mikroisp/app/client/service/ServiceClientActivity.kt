package com.loogika.mikroisp.app.client.service

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.loogika.mikroisp.app.R
import com.loogika.mikroisp.app.client.ApiService.RetrofitService
import com.loogika.mikroisp.app.client.ApiService.clientApi
import com.loogika.mikroisp.app.client.DetailClientActivity
import com.loogika.mikroisp.app.client.entity.ClientPost
import com.loogika.mikroisp.app.client.entity.ServicePost
import com.loogika.mikroisp.app.client.toast.ImprimirResultado
import com.loogika.mikroisp.app.client.validarForm.ValFormNewService
import com.loogika.mikroisp.app.client.validarForm.ValidarFormAssign
import com.loogika.mikroisp.app.databinding.ActivityServiceClientBinding
import com.loogika.mikroisp.app.interceptor.HeaderInterceptor
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.jar.Manifest

class ServiceClientActivity : AppCompatActivity(), OnMapReadyCallback,
    GoogleMap.OnMyLocationClickListener {
    lateinit var binding: ActivityServiceClientBinding
    lateinit var map: GoogleMap
    var planType: Int = 0
    var idClient: Int = 0
    var id: Int = 1
    var mostrar: String = "no"

    companion object {
        const val REQUEST_CODE_LOCATION = 200
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceClientBinding.inflate(layoutInflater)
        setContentView(binding.root)
        obtenerIdClient()
        ObtenerDatosSpinnerPlan()
        createFragment()

        if (mostrar == "mostrar") {
            showToolbar()
        }
        binding.save.setOnClickListener {
            validarDatos()
        }
    }

    fun showToolbar() {
        setSupportActionBar(binding.toolbarb)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun validarDatos() {
        val validar = ValFormNewService(
            planType.toString(),
            binding.description.editText?.text.toString(),
            binding.address.editText?.text.toString(),
            binding.latitud.editText?.text.toString(),
            binding.longuitud.editText?.text.toString(),
            binding
        )

        val result = arrayOf(
            validar.validarPlan(),
            validar.validarDescription(),
            validar.validarDirection(),
            validar.validarLatitud(),
            validar.validarLongitud()
        )

        if(false in result){
            return
        }
        guardarDatosServidor()
    }

    fun guardarDatosServidor(){
        try {
            val service = crearObjetoService()
            guarDatosService(service)
            ImprimirResultado.successResultadoService(this)
        } catch (e: ArithmeticException) {
            ImprimirResultado.cancelarResultadoService(this)
        }

    }


    fun obtenerIdClient() {
        mostrar = intent.getStringExtra("mostrar")!!
        idClient = intent.getIntExtra("id", 0)
    }

    private fun crearObjetoService(): ServicePost {
        return ServicePost(
            planType,
            1,
            idClient,
            1,
            binding.description.editText?.text.toString(),
            binding.address.editText?.text.toString(),
            binding.latitud.editText?.text.toString().toFloat(),
            binding.longuitud.editText?.text.toString().toFloat()
        )
    }

    fun createFragment() {
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.googleMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        enableLocation()
        map.setOnMyLocationClickListener(this)
        map.uiSettings.isZoomControlsEnabled = true
    }

    fun isLocationPermission() = ContextCompat.checkSelfPermission(
        this,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    @SuppressLint("MissingPermission")
    fun enableLocation() {
        if (!::map.isInitialized) return
        if (isLocationPermission()) {
            map.isMyLocationEnabled = true

        } else {
            requestPermission()
        }
    }

    fun requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            Toast.makeText(this, "Dirigase a ajustes y acepte los permisos", Toast.LENGTH_SHORT)
                .show()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION
            )
        }
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_LOCATION ->
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    map.isMyLocationEnabled = true
                } else {
                    Toast.makeText(
                        this,
                        "Para activar la localizacion ve a ajustes y aceptar",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            else -> {}
        }
    }

    @SuppressLint("MissingPermission")
    override fun onResumeFragments() {
        super.onResumeFragments()
        if (!::map.isInitialized) return
        if (!isLocationPermission()) {
            map.isMyLocationEnabled = false
            Toast.makeText(
                this,
                "Para activar la localizacion ve a ajustes y aceptar",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun createMarker(latitud: Double, longitud: Double) {
        val marker = LatLng(latitud, longitud)
        map.addMarker(MarkerOptions().position(marker).title("Ubicación actual"))
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(marker, 20f), 4000, null)
    }

    override fun onMyLocationClick(p0: Location) {
        createMarker(p0.latitude, p0.longitude)
        binding.latitud.editText?.setText(p0.latitude.toString())
        binding.longuitud.editText?.setText(p0.longitude.toString())
    }

    fun ObtenerDatosSpinnerPlan() {
        val plan = resources.getStringArray(R.array.plan)
        val adapter = ArrayAdapter(this, R.layout.items_spinner, plan)
        binding.autoCompletePlan.setAdapter(adapter)
        binding.autoCompletePlan.setOnItemClickListener { AdapterView, view, i, l ->
            val typePlan = AdapterView.getItemAtPosition(i).toString()
            when (typePlan) {
                "Plan 1 - 5 Megas" -> {
                    planType = 1
                    true
                }
                "Plan 2 - 8 Megas" -> {
                    planType = 2
                    true
                }
                "Plan 3 - 11 Megas" -> {
                    planType = 3
                    true
                }

                else -> {
                    planType = 0
                }
            }
        }
    }

    private fun guarDatosService(service: ServicePost) { // funcion para obtener los datos del api
        CoroutineScope(Dispatchers.IO).launch {
            val call = RetrofitService.getRetrofitService().create(clientApi::class.java)
                .createServiceClient(service)
                .execute()
            val puppies = call.body()
            runOnUiThread {
                if (call.isSuccessful) {
                    var serviceResponse = puppies!!.service
                    id = serviceResponse.id
                    enviarDatos(id)
                    Log.d("id", serviceResponse.id.toString())
                } else {
                    Log.d("error cancelado", "solicitud fue abortada")
                }

            }
        }
    }

    fun enviarDatos(idService: Int) {
        finish()
        val intent = Intent(this, AssignedDeviceActivity::class.java)
        intent.putExtra("id", idService)
        startActivity(intent)
    }

}