package com.loogika.mikroisp.app.report

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.print.PrintAttributes
import android.print.PrintManager
import android.view.Menu
import android.view.MenuItem
import com.loogika.mikroisp.app.R
import com.loogika.mikroisp.app.databinding.ActivityShowReportBinding
import com.loogika.mikroisp.app.payment.PaymentActivity
import com.loogika.mikroisp.app.payment.adapter.PdfDocumentAdapter
import java.io.File
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class ShowReportActivity : AppCompatActivity() {
    lateinit var binding: ActivityShowReportBinding
    lateinit var endDate:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowReportBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showToolbar()
        endDate = intent.getStringExtra("endDate").toString()
        binding.pdfView.fromFile(getFilePath("reporte-$endDate")).load()
        binding.cancelar.setOnClickListener {
            finish()
            val intent = Intent(this, ReportActivity::class.java)
            startActivity(intent)
        }
    }

    fun showToolbar(){
        setSupportActionBar(binding.toolbarb)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    fun printPdf(){
        val printManager : PrintManager = getSystemService(Context.PRINT_SERVICE) as PrintManager
        try {
            val printAdapter = PdfDocumentAdapter(getFilePath("reporte-$endDate")!!.absolutePath)
            printManager.print("Document", printAdapter, PrintAttributes.Builder().build())
        } catch (e : Exception) {

        }
    }

    private fun getFilePath(userLastName:String): File? {
        val contextWrapper = ContextWrapper(applicationContext)
        val documentDirectory = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        val file = File(documentDirectory, "fac-$userLastName.pdf")
        return file
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bottom_navigation_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.print->{
                printPdf()
                true
            }
            else->{}
        }
        return super.onOptionsItemSelected(item)
    }
}