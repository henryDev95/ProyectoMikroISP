package com.loogika.mikroisp.app.report

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.*
import android.graphics.pdf.PdfDocument
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.loogika.mikroisp.app.R
import com.loogika.mikroisp.app.client.adapter.ClientAdapter
import com.loogika.mikroisp.app.databinding.ActivityReportBinding
import com.loogika.mikroisp.app.report.adapter.ReportAdapter
import com.loogika.mikroisp.app.report.entity.Report
import com.loogika.mikroisp.app.report.toast.ShowResult
import com.loogika.mikroisp.app.report.webservice.ReportService
import com.loogika.mikroisp.app.report.webservice.RetrofitService
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class ReportActivity : AppCompatActivity() {
    lateinit var binding: ActivityReportBinding
    var reportList = mutableListOf<Report>()
    lateinit var bitmap: Bitmap
    lateinit var scaleBitmap: Bitmap
    lateinit var date: Date
    lateinit var dateBakend: Date
    lateinit var formateDate: DateFormat
    lateinit var FormatTime: DateFormat
    lateinit var FormatBakendDate: DateFormat
    var startDate: String = ""
    var endDate: String = ""
    var dayStart: Int = 0
    var monthStart: Int = 0
    var yearStart: Int = 0
    private lateinit var reportAdapter: ReportAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showToolbar()
        binding.viewPaymentList.isVisible = false
        binding.startDate.setOnClickListener {
            showDatePikerDialog()
        }
        binding.finaltDate.setOnClickListener {
            showDatePikerDialogDos()
        }

        binding.showReport.setOnClickListener {
            createPDf()
            finish()
            mostrarReporteCobros()
        }
    }

    private fun mostrarReporteCobros() {
        val intent = Intent(this, ShowReportActivity::class.java)
        intent.putExtra("endDate", endDate)
        startActivity(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun mostrarShimmer() {
        binding.viewPaymentList.isVisible = true
        binding.paymentsList.layoutManager = LinearLayoutManager(this)
        buscarRegistroReport("", startDate, endDate)
        Handler(Looper.getMainLooper()).postDelayed({
            binding.viewPaymentList.isVisible = false
            binding.paymentsList.isVisible = true
        }, 3500)
    }

    fun buscarRegistroReport(dni: String, starDate: String, endDate: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = RetrofitService.getRetrofil().create(ReportService::class.java)
            val executeApi = call.getReportDate(dni, starDate, endDate).execute()
            val report = executeApi.body()
            runOnUiThread {
                if (executeApi.isSuccessful) {
                    binding.paymentsList.isVisible = true
                    val reports = report?.report ?: emptyList()
                    if (reports.isNotEmpty()) {
                        reportList.clear()
                        reportList.addAll(reports)
                        reportAdapter = ReportAdapter(reportList)
                        binding.paymentsList.adapter = reportAdapter
                    } else {
                        reportList.clear()
                        reportAdapter = ReportAdapter(reportList)
                        binding.showReport.isEnabled = false
                        binding.startDate.setText("")
                        binding.finaltDate.setText("")
                        binding.logoReport.isVisible = true
                        binding.titulo.isVisible = false
                        binding.viewPaymentList.isVisible = false
                        binding.showReport.setBackgroundColor(resources.getColor(R.color.colorDesactivado))
                        ShowResult.sucessResultList(this@ReportActivity)
                    }
                } else {
                    return@runOnUiThread
                    Log.d("error cancelado", "solicitud fue abortada")
                }
            }
        }
    }

    private fun showDatePikerDialog() {
        val datepiker = DatePikerFragment { day, month, year -> onDateSelector(day, month, year) }
        datepiker.show(supportFragmentManager, "DatePiker")
    }

    private fun showDatePikerDialogDos() {
        val datePiker2 = DatePikerFragment { day, month, year -> onDateSelector2(day, month, year) }
        datePiker2.show(supportFragmentManager, "DatePiker")
    }

    fun onDateSelector(day: Int, month: Int, year: Int) {
        binding.startDate.setText("${year}-${month+1}-${day}")
        dayStart = day
        monthStart = month+1
        yearStart = year
        startDate = binding.startDate.text.toString()
        binding.fechaDesde.isEnabled = true
    }

    fun onDateSelector2(day: Int, month: Int, year: Int) {
        binding.paymentsList.isVisible = false
        if (validaFecha(day, month+1, year)) {
            binding.finaltDate.setText("${year}-${month+1}-${day}")
            endDate = binding.finaltDate.text.toString()
            binding.logoReport.isVisible = false
            binding.showReport.isEnabled = true
            binding.showReport.setBackgroundColor(resources.getColor(R.color.colorFondo))
            binding.titulo.isVisible = true
            mostrarShimmer()
        } else {
            binding.startDate.setText("")
            binding.finaltDate.setText("")
            binding.fechaDesde.isEnabled = false
            binding.titulo.isVisible = false
            binding.logoReport.isVisible = true
            ShowResult.errorResuulDate(this)
        }
    }

    fun validaFecha(day: Int, month: Int, year: Int): Boolean {

        return if(year == yearStart){
            (month >= monthStart) && (day >= dayStart)
        }else{
            (year > yearStart)
        }
    }

    fun showToolbar() {
        setSupportActionBar(binding.toolbarb)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun createPDf() {
        var pdfDocument = PdfDocument()
        var titulo = Paint()
        var heightPage = 2010
        var widthPage = 1200
        var cantidad = 1
        var total = 0f
        var longuitudIncial = 0
        var longuitudFinal = 16
        var sizeList = reportList.size
        var numeroPaginas = (sizeList/16).toInt()+1

        for (l in 1..numeroPaginas) {
            var paint = Paint()
            var paginaInfor = PdfDocument.PageInfo.Builder(widthPage, heightPage,l).create()
            var pagina1 = pdfDocument.startPage(paginaInfor)
            var canvas = pagina1.canvas

            bitmap = BitmapFactory.decodeResource(resources, R.drawable.logo_loogika)
            scaleBitmap = Bitmap.createScaledBitmap(bitmap, 300, 200, false)
            canvas.drawBitmap(scaleBitmap, 0f, 0f, paint)


            // para  otro texto

            date = Date()
            formateDate = SimpleDateFormat("yyyy/MM/dd")
            FormatTime = SimpleDateFormat("HH:mm:ss")
            paint.setColor(Color.rgb(0, 113, 118))
            paint.textSize = 20f
            paint.textAlign = Paint.Align.RIGHT
            canvas.drawText("Fecha Emitida:" + formateDate.format(date), 1100f, 100f, paint)
            canvas.drawText("Hora:" + FormatTime.format(date), 1100f, 150f, paint)


            // texto de titulo
            titulo.textAlign = Paint.Align.CENTER
            titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
            titulo.textSize = 30f
            canvas.drawText("REPORTE DE COBROS REALIZADOS", 1200f / 2f, 250f, titulo)

            // para el numero de factura
            paint.textAlign = Paint.Align.LEFT
            canvas.drawText("Fecha Inicial:" + startDate, 20f, 350f, paint)
            canvas.drawText("Fecha Final:" + endDate, 20f, 400f, paint)

            // para poner el cuadro
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 2f
            canvas.drawRect(5f, 450f, 1200f - 5f, 550f, paint)
            paint.textAlign = Paint.Align.LEFT
            paint.style = Paint.Style.FILL
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
            paint.textSize = 20f
            canvas.drawText("Nª", 50f, 510f, paint)
            canvas.drawText("Nombre", 300f, 510f, paint)
            canvas.drawText("Cédula", 650f, 510f, paint)
            canvas.drawText("Fecha", 900f, 510f, paint)
            canvas.drawText("Valor", 1100f, 510f, paint)

            canvas.drawLine(120f, 450f, 120f, 550f, paint)
            canvas.drawLine(550f, 450f, 550f, 550f, paint)
            canvas.drawLine(800f, 450f, 800f, 550f, paint)
            canvas.drawLine(1050f, 450f, 1050f, 550f, paint)

            paint.textSize = 20f
            paint.color = Color.BLACK
            FormatBakendDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val formatat = SimpleDateFormat("dd MMM yyyy HH:mm:ss")
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL))

            var i = 0f

            if (l == numeroPaginas) {

                for (j in longuitudIncial..sizeList-1) {
                    canvas.drawText(cantidad.toString(), 30f, 600f + i, paint)
                    canvas.drawText(
                        reportList[j].invoice.client.userFirstName!! + " " + reportList[j].invoice.client.userLastName!!,
                        150f,
                        600f + i,
                        paint
                    )
                    canvas.drawText(reportList[j].invoice.client.dni!!, 610f, 600f + i, paint)
                    dateBakend = FormatBakendDate.parse(reportList[j].createdAt!!)
                    canvas.drawText(formatat.format(dateBakend), 830f, 600f + i, paint)
                    canvas.drawText(reportList[j].value.toString(), 1090f, 600f + i, paint)
                    i += 90f
                    cantidad++
                    total += reportList[j].value
                }


                paint.textAlign = Paint.Align.RIGHT
                paint.textSize = 30f
                paint.color = Color.BLACK
                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
                canvas.drawText("Total:", 900f, 600f + i, paint)

                // valor total
                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL))
                canvas.drawText(((total * 100.0).roundToInt()/100.0).toString(), 1090f, 600f + i, paint)


            }else{
                for (j in longuitudIncial..longuitudFinal-1) {
                    canvas.drawText(cantidad.toString(), 30f, 600f + i, paint)
                    canvas.drawText(
                        reportList[j].invoice.client.userFirstName!! + " " + reportList[j].invoice.client.userLastName!!,
                        150f,
                        600f + i,
                        paint
                    )
                    canvas.drawText(reportList[j].invoice.client.dni!!, 610f, 600f + i, paint)
                    dateBakend = FormatBakendDate.parse(reportList[j].createdAt!!)
                    canvas.drawText(formatat.format(dateBakend), 830f, 600f + i, paint)
                    canvas.drawText(reportList[j].value.toString(), 1090f, 600f + i, paint)
                    i += 90f
                    cantidad++
                    total += reportList[j].value
                }
                longuitudIncial += 16
                longuitudFinal+=16

            }
            pdfDocument.finishPage(pagina1)
        }

        try {
            pdfDocument.writeTo(FileOutputStream(getFilePath()))
            ShowResult.sucessResuulPdf(this)
        } catch (e: IOException) {
            Toast.makeText(this, "fallo" + e.printStackTrace().toString(), Toast.LENGTH_SHORT)
                .show()
        }
        pdfDocument.close()

    }

    private fun getFilePath(): String? {
        val contextWrapper = ContextWrapper(applicationContext)
        val documentDirectory = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        val file = File(documentDirectory, "fac-reporte-$endDate.pdf")
        return file.path
    }

}