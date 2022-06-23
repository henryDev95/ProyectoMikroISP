package com.loogika.mikroisp.app.client

import android.content.Context
import android.content.DialogInterface
import android.hardware.Camera
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.loogika.mikroisp.app.R
import com.loogika.mikroisp.app.client.ApiService.clientApi
import com.loogika.mikroisp.app.client.entity.Client
import com.loogika.mikroisp.app.client.entity.ClientPost
import com.loogika.mikroisp.app.databinding.ActivityEditClientBinding
import com.loogika.mikroisp.app.interceptor.HeaderInterceptor
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EditClientActivity : AppCompatActivity(),AdapterView.OnItemClickListener {
    lateinit var binding: ActivityEditClientBinding
    private var typeClient: Int = 0
    var id:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditClientBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showDateClient()

        binding.save.setOnClickListener {
            var cliente = crearObjetoClient()
            Toast.makeText(this, cliente.email, Toast.LENGTH_SHORT).show()
            mostrarDialog(it.context, cliente , id)
        }

        binding.buttCancel.setOnClickListener {
            cancelarResultado()
            finish()
        }
    }

    fun showDateClient(){
        val client = intent.getParcelableExtra<Client>("client")!!
        id = client.id
        binding.dni.editText?.setText(client.dni).toString()
        binding.firstName.editText?.setText(client.userFirstName).toString()
        binding.lastName.editText?.setText(client.userLastName).toString()
        binding.address.editText?.setText(client.address).toString()
        binding.telephone.editText?.setText(client.phone1).toString()
        if(client.type == 1){
            binding.autoCompleteText.hint= "Residencial"
        }else{
            binding.autoCompleteText.hint= "Empresarial"
        }
        binding.email.editText?.setText(client.email).toString()
        binding.descripcion.editText?.setText(client.description).toString()
        ObtenerDatosSpinner()


    }


    private fun mostrarDialog(contex: Context ,cliente:ClientPost , idCliente:Int) {
        val builder = AlertDialog.Builder(contex)
        builder.setTitle("Editar")
            .setMessage("Deseas editar los datos del cliente?")
            .setPositiveButton(R.string.accept,
                DialogInterface.OnClickListener { dialog, id ->
                    try{
                        guarDatos(cliente,idCliente)
                        Log.d("cliente",cliente.toString())
                    }catch (e: ArithmeticException){
                        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
                    }

                })
            .setNegativeButton(R.string.cancel,
                DialogInterface.OnClickListener { dialog, id ->
                    cancelarResultado()
                    finish()
                })
        builder.show()
    }

    fun ObtenerDatosSpinner(){
        val tipoCliente = resources.getStringArray(R.array.TypeClient)
        val adapter = ArrayAdapter(this, R.layout.items_spinner,tipoCliente)
        with(binding.autoCompleteText){
            setAdapter(adapter)
            onItemClickListener = this@EditClientActivity
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val type = parent?.getItemAtPosition(position).toString()
        if (type == "Residencial") {
            typeClient = 1
        } else {
            typeClient = 2
        }
    }


    private fun guarDatos(clientePost: ClientPost , id:Int) { // funcion para obtener los datos del api
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(clientApi::class.java).editClient(clientePost,id)
                .execute()
            val puppies = call.body()
            runOnUiThread {
                if (call.isSuccessful) {
                    Log.d("Exito", puppies.toString())
                } else {
                    Log.d("error cancelado", "solicitud fue abortada")
                }

            }
        }
    }


    private fun getRetrofit(): Retrofit { // funcion de retrofil
        var urlBase = "http://192.168.2.253/proyectos-web/adminwisp/web/app_dev.php/api/v1/client/"
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

    private fun crearObjetoClient(): ClientPost {
        return ClientPost(
            typeClient,
            binding.dni.editText?.text.toString(),
            binding.firstName.editText?.text.toString(),
            binding.lastName.editText?.text.toString(),
            binding.address.editText?.text.toString(),
            "Pujíli",
            "EC",
            1,
            binding.telephone.editText?.text.toString(),
            binding.email.editText?.text.toString(),
            0,
            true,
            true,
            false,
            true,
            binding.descripcion.editText?.text.toString()
        )
    }


    fun cancelarResultado() {
        val toast = FancyToast.makeText(
            this,
            "No se realizo el registro del cliente!",
            FancyToast.LENGTH_LONG,
            FancyToast.WARNING,
            false
        )
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }
}