package com.loogika.mikroisp.app.client.service

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
import com.loogika.mikroisp.app.client.entity.Client
import com.loogika.mikroisp.app.client.entity.ClientPost
import com.loogika.mikroisp.app.client.entity.ServicePost
import com.loogika.mikroisp.app.client.service.entity.ServiceEdit
import com.loogika.mikroisp.app.client.toast.ImprimirResultado
import com.loogika.mikroisp.app.databinding.ActivityEditServiceBinding
import com.loogika.mikroisp.app.databinding.ActivityListServiceBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditServiceActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMyLocationClickListener {
    lateinit var binding: ActivityEditServiceBinding
    lateinit var map: GoogleMap
    var planType: Int = 0
    var idClient:Int = 0
    var idService:Int=0
    var latitud: Double = 0.0
    var longitud: Double = 0.0

    companion object {
        const val REQUEST_CODE_LOCATION = 200
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showTolbar()
        showDateService()
        createFragment()
        binding.save.setOnClickListener {
            val service = crearObjetoService()
            mostrarDialog(this, service,idService)
        }

        binding.buttCancel.setOnClickListener {
            finish()
            ImprimirResultado.cancelarResultadoServiceEdit(this)
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    fun showTolbar() {
        setSupportActionBar(binding.toolbarb)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun showDateService() {
        val client = intent.getParcelableExtra<Client>("client")!!
        latitud = client.services[0].latitude!!.toDouble()
        longitud = client.services[0].longitude!!.toDouble()
        idClient = client.id
        idService = client.services[0].id
        binding.autoCompletePlan.setText(client.services[0].plan.name!!)
        binding.description.editText?.setText(client.services[0].description!!)
        binding.address.editText?.setText(client.services[0].address!!)
        binding.latitud.editText?.setText(client.services[0].latitude!!.toDouble().toString())
        binding.longuitud.editText?.setText(client.services[0].longitude!!.toDouble().toString())

        if (client.services[0].plan != null) {
            planType = client.services[0].plan.id
        }
    }


    private fun crearObjetoService(): ServiceEdit {
        return ServiceEdit(
            idClient,
            planType,
            binding.description.editText?.text.toString(),
            binding.address.editText?.text.toString(),
            binding.latitud.editText?.text.toString().toFloat(),
            binding.longuitud.editText?.text.toString().toFloat()
        )
    }


    fun guardarDatosServidor( service: ServiceEdit , idService:Int){
        try {
            guarDatosService(service, idService)
            ImprimirResultado.successResultadoServiceEdit(this)
        } catch (e: ArithmeticException) {
            ImprimirResultado.cancelarResultadoServiceEdit(this)
        }

    }

    private fun guarDatosService(service: ServiceEdit , serviceId:Int) { // funcion para obtener los datos del api
        CoroutineScope(Dispatchers.IO).launch {
            val call = RetrofitService.getRetrofitService().create(clientApi::class.java)
                .editService(service,serviceId)
                .execute()
            val puppies = call.body()
            runOnUiThread {
                if (call.isSuccessful) {
                    var serviceResponse = puppies!!.service

                    Log.d("id", serviceResponse.id.toString())
                } else {
                    Log.d("error cancelado", "solicitud fue abortada")
                }
            }
        }
    }


    private fun mostrarDialog(contex: Context, service: ServiceEdit, idService:Int) {
        val builder = AlertDialog.Builder(contex)
        builder.setTitle("Editar")
            .setMessage("Deseas editar el servicio?")
            .setPositiveButton(R.string.accept,
                DialogInterface.OnClickListener { dialog, id ->
                    try{
                        guardarDatosServidor(service, idService)

                    }catch (e: ArithmeticException){
                        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
                    }

                })
            .setNegativeButton(R.string.cancel,
                DialogInterface.OnClickListener { dialog, id ->
                       finish()
                    ImprimirResultado.cancelarResultadoServiceEdit(this)
                })
        builder.show()
    }




    fun createFragment() {
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.googleMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        createMarker(latitud, longitud)
        enableLocation()
        map.setOnMyLocationClickListener(this)
        map.uiSettings.isZoomControlsEnabled = true

    }

    override fun onMyLocationClick(p0: Location) {
        createMarker(p0.latitude, p0.longitude)
        binding.latitud.editText?.setText(p0.latitude.toString())
        binding.longuitud.editText?.setText(p0.longitude.toString())
    }

    fun createMarker(latitud: Double, longitud: Double) {
        val marker = LatLng(latitud, longitud)
        map.addMarker(MarkerOptions().position(marker).title("Ubicación actual"))
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(marker, 18f), 4000, null)
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
                ServiceClientActivity.REQUEST_CODE_LOCATION
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
            ServiceClientActivity.REQUEST_CODE_LOCATION ->
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

}