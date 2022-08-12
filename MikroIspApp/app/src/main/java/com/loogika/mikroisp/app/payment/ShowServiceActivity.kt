package com.loogika.mikroisp.app.payment

import android.Manifest
import android.content.Context
import android.content.ContextWrapper
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import android.graphics.pdf.PdfDocument
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.loogika.mikroisp.app.R
import com.loogika.mikroisp.app.client.ApiService.clientApi
import com.loogika.mikroisp.app.client.entity.ClientPost
import com.loogika.mikroisp.app.databinding.ActivityShowServiceBinding
import com.loogika.mikroisp.app.interceptor.HeaderInterceptor
import com.loogika.mikroisp.app.payment.apiService.PaymentApi
import com.loogika.mikroisp.app.payment.apiService.RetrofilServicePayment
import com.loogika.mikroisp.app.payment.entity.PaymentPost
import com.loogika.mikroisp.app.payment.entity.Plan
import com.loogika.mikroisp.app.payment.entity.Service
import com.loogika.mikroisp.app.payment.toast.ImprimirResultado
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import org.w3c.dom.Document
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class ShowServiceActivity : AppCompatActivity() {
    lateinit var binding: ActivityShowServiceBinding
    lateinit var bitmap: Bitmap
    lateinit var scaleBitmap: Bitmap
    lateinit var date: Date
    lateinit var formateDate: DateFormat
    lateinit var FormatTime: DateFormat
    var IdInvoice: Int = 0
    var IdService: Int = 0
    var numberInvoice: String = ""
    var value: Float = 0f
    var dni: String = ""
    var userFirstName: String = ""
    var userLastName: String = ""
    var address: String = ""
    var telephone: String = ""
    lateinit var service:Service
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showToolbar()
        showDateClient()
        binding.cobrarButton.setOnClickListener {
            mostrarDialog(it.context)
        }

        binding.cancelarButton.setOnClickListener {
            ImprimirResultado.cancelarResultado(this)
            finish()
        }

        binding.pdfInvoice.setOnClickListener{
            createPDf()
            finish()
            val intent = Intent(this, FacturaActivity::class.java)
            intent.putExtra("userLastName", userLastName)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    fun showToolbar() {
        setSupportActionBar(binding.toolbarb)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }


    fun showDateClient() {
        IdInvoice = intent.getIntExtra("idInvoice", 0)
        service = intent.getParcelableExtra<Service>("service")!!
        IdService = service.id
        numberInvoice = intent.getStringExtra("numberInvoice").toString()
        value = intent.getFloatExtra("total", 0f)
        dni = intent.getStringExtra("dni").toString()
        userFirstName = intent.getStringExtra("userFirstName").toString()
        userLastName = intent.getStringExtra("userLastName").toString()
        address = intent.getStringExtra("address").toString()
        telephone = intent.getStringExtra("telephone").toString()
        binding.detailIdentification.text = dni.toString()
        binding.detailNames.text = userFirstName.toString()
        binding.detailSurname.text = userLastName.toString()
        binding.detailDirecction.text = address.toString()
        binding.detailTelephone.text = telephone.toString()
        binding.detailPlaName.text = service.plan.name.toString()
        if (service.plan.status == "true") {
            binding.detailPlanState.text = "Activo"
        } else {
            binding.detailPlanState.text = "Cancelado"
        }
        binding.detailPlanValue.text = service.plan.fullValue.toString()
    }

    private fun mostrarDialog(contex: Context) {
        val builder = AlertDialog.Builder(contex)
        builder.setTitle("Cobrar")
            .setMessage("Desea cobrar el servicio de internet?")
            .setPositiveButton(R.string.accept,
                DialogInterface.OnClickListener { dialog, id ->
                    ImprimirResultado.successResultado(this)
                    binding.pdfInvoice.isVisible = true
                    /*val payment = createObjectPayment()
                    try {
                        guarDatos(payment)

                    } catch (e: ArithmeticException) {
                        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
                    }
                    */
                    //binding.pdfInvoice.isVisible = true
                })
            .setNegativeButton(R.string.cancel,
                DialogInterface.OnClickListener { dialog, id ->
                    ImprimirResultado.cancelarResultado(this)
                    finish()
                })
        builder.show()
    }

    private fun createPDf() {
        var pdfDocument = PdfDocument()
        var paint = Paint()
        var titulo = Paint()
        var paginaInfor = PdfDocument.PageInfo.Builder(1200, 2010, 1).create()
        var pagina1 = pdfDocument.startPage(paginaInfor)
        var canvas = pagina1.canvas

        // para insertar la imagen al pdf
        bitmap = BitmapFactory.decodeResource(resources, R.drawable.logo_loogika)
        scaleBitmap = Bitmap.createScaledBitmap(bitmap, 300, 200, false)
        canvas.drawBitmap(scaleBitmap, 0f, 0f, paint)

        // para  otro texto
        paint.setColor(Color.rgb(0, 113, 118))
        paint.textSize = 20f
        paint.textAlign = Paint.Align.RIGHT
        canvas.drawText("Direc: Av. Velasco Ibarra,", 1140f, 100f, paint)
        canvas.drawText("Jose Joaquin de olmedo", 1140f, 150f, paint)
        canvas.drawText("Pujili, Cotopaxi, Ecuador", 1140f, 200f, paint)
        canvas.drawText("Teléf:(03) 2724-769", 1140f, 250f, paint)
        canvas.drawText("Correo Eletró:loogika@gmail.com", 1140f, 300f, paint)

        // texto de titulo

        titulo.textAlign = Paint.Align.CENTER
        titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD_ITALIC))
        titulo.textSize = 40f
        canvas.drawText("COMPROBANTE DE PAGO", 1200f / 2f, 350f, titulo)

        // datos de informacion

        paint.textAlign = Paint.Align.LEFT
        paint.textSize = 35f
        paint.color = Color.BLACK
        canvas.drawText("Cédula :" + dni, 30f, 450f, paint)
        canvas.drawText("Nombres :" + userFirstName, 30f, 500f, paint)
        canvas.drawText("Apellidos :" + userLastName, 30f, 550f, paint)
        canvas.drawText("Direccion:" + address, 30f, 600f, paint)
        canvas.drawText("Ciudad:" + "Pujili,Ecuador", 30f, 650f, paint)
        canvas.drawText("Teléfono:" + telephone, 30f, 700f, paint)

        // para el numero de factura
        date = Date()
        formateDate = SimpleDateFormat("dd/mm/yy")
        FormatTime = SimpleDateFormat("HH:mm:ss")
        paint.textAlign = Paint.Align.RIGHT
        canvas.drawText("Número del comprobante:" + numberInvoice, 1200f - 30f, 450f, paint)
        canvas.drawText("Fecha:" + formateDate.format(date), 1200f - 30f, 500f, paint)
        canvas.drawText("Hora:" + FormatTime.format(date), 1200f - 30f, 550f, paint)
        canvas.drawText("Estado:" + "Pagado", 1200f - 30f, 600f, paint)

        // para poner el cuadro
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 2f
        canvas.drawRect(20f, 750f, 1200f - 20f, 850f, paint)
        paint.textAlign = Paint.Align.LEFT
        paint.style = Paint.Style.FILL
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
        paint.textSize = 40f
        canvas.drawText("Descripción", 200f, 810f, paint)
        canvas.drawText("Cantidad", 580f, 810f, paint)
        canvas.drawText("Precio", 800f, 810f, paint)
        canvas.drawText("Total", 1000f, 810f, paint)

        canvas.drawLine(550f, 750f, 550f, 850f, paint)
        canvas.drawLine(750f, 750f, 750f, 850f, paint)
        canvas.drawLine(950f, 750f, 950f, 850f, paint)

        paint.textSize = 35f
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL))
        canvas.drawText("Internet," + service.plan.name, 100f, 950f, paint)
        canvas.drawText("1", 630f, 950f, paint)
        canvas.drawText(service.plan.value.toString(), 830f, 950f, paint)
        canvas.drawText(service.plan.value.toString(), 1020f, 950f, paint)

        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
        paint.textSize = 35f
        canvas.drawText("SubTotal:", 800f, 1050f, paint)
        canvas.drawText("Impuesto(12%):", 700f, 1100f, paint)
        canvas.drawText("Total: ", 860f, 1150f, paint)

        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL))
        paint.textSize = 35f
        canvas.drawText(service.plan.value.toString(), 1020f, 1050f, paint)
        when (service.plan.id) {
            1 -> canvas.drawText("2.40", 1020f, 1100f, paint)
            2 -> canvas.drawText("3.36", 1020f, 1100f, paint)
            else -> canvas.drawText("4.32", 1020f, 1100f, paint)
        }

        canvas.drawText(service.plan.fullValue.toString(), 1020f, 1150f, paint)
        pdfDocument.finishPage(pagina1)
        try {
            pdfDocument.writeTo(FileOutputStream(getFilePath()))
            ImprimirResultado.sucessResuulPdf(this)
        } catch (e: IOException) {
            Toast.makeText(this, "fallo" + e.printStackTrace().toString(), Toast.LENGTH_SHORT)
                .show()
        }
        pdfDocument.close()

    }

    private fun getFilePath(): String? {
        val contextWrapper = ContextWrapper(applicationContext)
        val documentDirectory = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        val file = File(documentDirectory, "fac-"+userLastName+".pdf")
        return file.path
    }

    fun seleccionarPermiso(): Boolean {
        val permiso1 = ContextCompat.checkSelfPermission(
            applicationContext,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        val permiso2 = ContextCompat.checkSelfPermission(
            applicationContext,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        return permiso1 == PackageManager.PERMISSION_GRANTED && permiso2 == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermiso() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ),
            200
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 200) {
            if (grantResults.size > 0) {
                val writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED
                val readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED
                if (writeStorage && readStorage) {
                    Toast.makeText(this, "Permiso concedido", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Permiso rechazado", Toast.LENGTH_LONG).show()
                    finish()
                }
            }
        }
    }


    private fun guarDatos(paymentPost: PaymentPost) { // funcion para obtener los datos del api

        CoroutineScope(Dispatchers.IO).launch {
            val call = RetrofilServicePayment.getRetrofitPayment().create(PaymentApi::class.java).createPayment(paymentPost)
                .execute()
            val puppies = call.body()
            runOnUiThread {
                if (call.isSuccessful) {
                    var paymentResponse = puppies!!.client
                    Log.d("id", paymentResponse.id.toString())
                } else {
                    Log.d("error cancelado", "solicitud fue abortada")
                }
            }
        }

    }

    fun createObjectPayment(): PaymentPost {
        return PaymentPost(
            IdInvoice,
            IdService,
            value,
            null
        )
    }

}