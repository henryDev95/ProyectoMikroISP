package com.loogika.mikroisp.app.client

import android.content.Intent
import android.os.Bundle
import android.system.ErrnoException
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.loogika.mikroisp.app.R
import com.loogika.mikroisp.app.client.ApiService.RetrofitService
import com.loogika.mikroisp.app.client.ApiService.clientApi
import com.loogika.mikroisp.app.client.entity.Client
import com.loogika.mikroisp.app.client.entity.ClientPost
import com.loogika.mikroisp.app.client.service.ServiceClientActivity
import com.loogika.mikroisp.app.client.toast.ImprimirResultado
import com.loogika.mikroisp.app.client.validarForm.ValidarForm
import com.loogika.mikroisp.app.databinding.ActivityNewClientBinding
import com.loogika.mikroisp.app.interceptor.HeaderInterceptor
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.util.logging.ErrorManager


class NewClientActivity : AppCompatActivity(), AdapterView.OnItemClickListener {
    private lateinit var binding: ActivityNewClientBinding
    var typeClient: Int = 0
    var id: Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewClientBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ObtenerDatosSpinner()
        showToolbar()
        binding.save.setOnClickListener {
            validarCampo()
            //val cliente = crearObjetoClient()
            // enviarDatos(cliente, id)
            /*
            try{
                guarDatos(cliente)
                ImprimirResultado.successResultado(this)
            }catch (e: ArithmeticException){
                ImprimirResultado.cancelarResultado(this)
            }
            */
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

    fun enviarDatos(cliente: ClientPost, idCli: Int) {
        val intent = Intent(this, DetailClientActivity::class.java)
        intent.putExtra("id", idCli)
        Toast.makeText(this, "id--->" + idCli.toString(), Toast.LENGTH_SHORT).show()
        intent.putExtra("type", cliente.type)
        intent.putExtra("dni", cliente.dni)
        intent.putExtra("userFirstName", cliente.user_first_name)
        intent.putExtra("userLastName", cliente.user_last_name)
        intent.putExtra("address", cliente.address)
        intent.putExtra("telephone", cliente.phone1)
        intent.putExtra("email", cliente.email)
        startActivity(intent)
    }

    fun validarCampo() {
        var validar = ValidarForm(
            binding.dni.editText?.text.toString(),
            binding.firstName.editText?.text.toString(),
            binding.lastName.editText?.text.toString(),
            binding.address.editText?.text.toString(),
            binding.telephone.editText?.text.toString(),
            binding.email.editText?.text.toString(),
            binding.descripcion.editText?.text.toString(),
            typeClient.toString(),
            binding
        )

        val result = arrayOf(
            validar.validarCedula(),
            validar.validarFistName(),
            validar.validarLastName(),
            validar.validarDirection(),
            validar.validarTelephone(),
            validar.validarEmail(),
            validar.validarDescription(),
            validar.validarType()
        )
        if (false in result) {
            return
        }
    }

    private fun guarDatos(clientePost: ClientPost) { // funcion para obtener los datos del api

        CoroutineScope(Dispatchers.IO).launch {
            val call = RetrofitService.getRetrofit().create(clientApi::class.java)
                .createClient(clientePost)
                .execute()
            val puppies = call.body()
            runOnUiThread {
                if (call.isSuccessful) {
                    var clientResponse = puppies!!.client
                    id = clientResponse.id
                    enviarDatos(clientePost, id)
                    Log.d("id", clientResponse.id.toString())
                } else {
                    Log.d("error cancelado", "solicitud fue abortada")
                }
            }
        }
    }

    private fun crearObjetoClient(): ClientPost {
        return ClientPost(
            typeClient,
            binding.dni.editText?.text.toString(),
            binding.firstName.editText?.text.toString(),
            binding.lastName.editText?.text.toString(),
            binding.address.editText?.text.toString(),
            binding.telephone.editText?.text.toString(),
            binding.email.editText?.text.toString(),
            binding.descripcion.editText?.text.toString()
        )
    }


    fun ObtenerDatosSpinner() {
        val tipoCliente = resources.getStringArray(R.array.TypeClient)
        val adapter = ArrayAdapter(this, R.layout.items_spinner, tipoCliente)
        with(binding.autoCompleteText) {
            setAdapter(adapter)
            onItemClickListener = this@NewClientActivity
        }
    }


    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val type = parent?.getItemAtPosition(position).toString()
        when(type){
            "Residencial" -> {
                typeClient = 1
                true
            }
            "Empresarial" -> {
                typeClient = 2
                true
            }
            else->{
                typeClient = 0
            }
        }
    }
}