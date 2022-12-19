package com.loogika.mikroisp.app.client.service

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.loogika.mikroisp.app.R
import com.loogika.mikroisp.app.client.entity.Client
import com.loogika.mikroisp.app.databinding.ActivityServiceShowBinding

class ServiceShowActivity : AppCompatActivity(), OnMapReadyCallback {
    lateinit var binding : ActivityServiceShowBinding
    lateinit var map: GoogleMap
    var latitud:Double=0.0
    var longitud:Double=0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivityServiceShowBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showTolbar()
        showDateService()
        createFragment()
        binding.btnclose.setOnClickListener {
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    fun showTolbar(){
        setSupportActionBar(binding.toolbarb)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    fun showDateService(){
        val client = intent.getParcelableExtra<Client>("client")!!
        var position = intent.getIntExtra("position",0)
        latitud = client.services[position].latitude!!.toDouble()
        longitud = client.services[position].longitude!!.toDouble()
        binding.plan.text = client.services[position].plan.name
        if(client.services[position].status == 1){
            binding.estado.text = "Activo"
        }else{
            binding.estado.text = "En corte"
        }

        Log.d("servicio--->" , client.toString())
        binding.antenaName.text = client.services[position].serviceDevices[0].device?.name
        binding.router.text = client.services[position].serviceDevices[1].device?.name
    }

    fun createFragment() {
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.googleMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.uiSettings.isZoomControlsEnabled = true
        createMarker(latitud,longitud)
    }
    fun createMarker(latitud:Double,longitud:Double){
        val marker = LatLng(latitud,longitud)
        map.addMarker(MarkerOptions().position(marker).title("Ubicación actual"))
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(marker,18f),4000,null)
    }
}