package com.loogika.mikroisp.app.client

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.loogika.mikroisp.app.R
import com.loogika.mikroisp.app.client.ApiService.RetrofitService
import com.loogika.mikroisp.app.client.ApiService.clientApi
import com.loogika.mikroisp.app.client.entity.Client
import com.loogika.mikroisp.app.client.entity.ClientPost
import com.loogika.mikroisp.app.client.toast.ImprimirResultado
import com.loogika.mikroisp.app.client.validarForm.ValidarForm
import com.loogika.mikroisp.app.client.validarForm.ValidarFormFormEdit
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
        ObtenerDatosSpinner()
        binding.save.setOnClickListener {
            var cliente = crearObjetoClient()
            mostrarDialog(it.context, cliente , id)
        }

        binding.buttCancel.setOnClickListener {
             finish()
            ImprimirResultado.cancelarResultadoClientEdit(this)
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
            binding.autoCompleteText.setText("Residencial")
            typeClient = 1
        }else{
            typeClient = 2
            binding.autoCompleteText.setText("Empresarial")
        }
        binding.email.editText?.setText(client.email).toString()
        binding.descripcion.editText?.setText(client.description).toString()
    }



    private fun mostrarDialog(contex: Context ,cliente:ClientPost , idCliente:Int) {
        val builder = AlertDialog.Builder(contex)
        builder.setTitle("Editar")
            .setMessage("Deseas editar los datos del cliente?")
            .setPositiveButton(R.string.accept,
                DialogInterface.OnClickListener { dialog, id ->
                    validarCampo(cliente,idCliente)

                })
            .setNegativeButton(R.string.cancel,
                DialogInterface.OnClickListener { dialog, id ->
                     finish()
                    ImprimirResultado.cancelarResultadoClientEdit(this)
                })
        builder.show()
    }


    fun validarCampo(client:ClientPost , id:Int) {
        var validar = ValidarFormFormEdit(
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
        guadarDatosServidor(client,id)
    }

    private fun guadarDatosServidor( cliente: ClientPost, idCliente:Int) {
        try{
            guarDatos(cliente,idCliente)
            ImprimirResultado.successResultadoClientEdit(this)
        }catch (e: ArithmeticException){
            ImprimirResultado.cancelarResultadoClientEdit(this)
        }
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
            val call = RetrofitService.getRetrofitClient().create(clientApi::class.java).editClient(clientePost,id)
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



}