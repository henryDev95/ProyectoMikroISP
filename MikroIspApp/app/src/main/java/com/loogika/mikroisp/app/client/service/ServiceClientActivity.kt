package com.loogika.mikroisp.app.client.service

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import com.loogika.mikroisp.app.databinding.ActivityServiceClientBinding
import java.util.jar.Manifest

class ServiceClientActivity : AppCompatActivity(), OnMapReadyCallback , GoogleMap.OnMyLocationClickListener{
    lateinit var binding: ActivityServiceClientBinding
    lateinit var map: GoogleMap

    companion object{
        const val REQUEST_CODE_LOCATION=200
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceClientBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ObtenerDatosSpinnerPlan()
        createFragment()
        binding.save.setOnClickListener {
            val intent =Intent(this, AssignedDeviceActivity::class.java)
            startActivity(intent)
        }

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
    fun enableLocation(){
        if(!::map.isInitialized) return
        if(isLocationPermission()){
            map.isMyLocationEnabled = true

        }else{
            requestPermission()
        }
    }

    fun requestPermission(){
          if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)){
              Toast.makeText(this, "Dirigase a ajustes y acepte los permisos", Toast.LENGTH_SHORT).show()
          }else{
              ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),REQUEST_CODE_LOCATION)
          }
    }
    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
           REQUEST_CODE_LOCATION->
               if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                   map.isMyLocationEnabled = true
               }else{
                   Toast.makeText(this, "Para activar la localizacion ve a ajustes y aceptar", Toast.LENGTH_SHORT).show()
               }
           else->{}
       }
    }

    @SuppressLint("MissingPermission")
    override fun onResumeFragments() {
        super.onResumeFragments()
        if(!::map.isInitialized) return
        if(!isLocationPermission()){
            map.isMyLocationEnabled = false
            Toast.makeText(this, "Para activar la localizacion ve a ajustes y aceptar", Toast.LENGTH_SHORT).show()
        }
    }

    fun createMarker(latitud:Double,longitud:Double){
        val marker = LatLng(latitud,longitud)
        map.addMarker(MarkerOptions().position(marker).title("Ubicación actual"))
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(marker,20f),4000,null)
    }

    override fun onMyLocationClick(p0: Location) {
        createMarker(p0.latitude , p0.longitude)
        binding.latitud.editText?.setText(p0.latitude.toString())
        binding.longuitud.editText?.setText(p0.longitude.toString())
    }

    fun ObtenerDatosSpinnerPlan(){
        val plan = resources.getStringArray(R.array.plan)
        val adapter = ArrayAdapter(this, R.layout.items_spinner,plan)
        binding.autoCompletePlan.setAdapter(adapter)
        binding.autoCompletePlan.setOnItemClickListener{ AdapterView, view, i,l->
            val typeDevice = AdapterView.getItemAtPosition(i).toString()

        }
    }


}