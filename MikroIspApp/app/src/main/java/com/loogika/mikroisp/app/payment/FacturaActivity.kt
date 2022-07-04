package com.loogika.mikroisp.app.payment

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import android.graphics.pdf.PdfDocument
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.print.PrintAttributes
import android.print.PrintManager
import android.text.TextPaint
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.EnvironmentCompat
import com.loogika.mikroisp.app.DashboardActivity
import com.loogika.mikroisp.app.R
import com.loogika.mikroisp.app.databinding.ActivityFacturaBinding
import com.loogika.mikroisp.app.payment.adapter.PdfDocumentAdapter
import com.shashank.sony.fancytoastlib.FancyToast
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class FacturaActivity : AppCompatActivity() {
    lateinit var binding: ActivityFacturaBinding
    var userLastName:String =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFacturaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarb)

        userLastName = intent.getStringExtra("userLastName").toString()
        binding.pdfView.fromFile(getFilePath(userLastName)).load()


        binding.cancelar.setOnClickListener {
            val intent = Intent(this,PaymentActivity::class.java)
            startActivity(intent)
        }

    }

    fun printPdf(){
        val printManager : PrintManager = getSystemService(Context.PRINT_SERVICE) as PrintManager
        try {
            val printAdapter = PdfDocumentAdapter(getFilePath(userLastName)!!.absolutePath)
            printManager.print("Document", printAdapter, PrintAttributes.Builder().build())
        } catch (e : Exception) {

        }
    }

    private fun getFilePath(userLastName:String): File? {
        val contextWrapper = ContextWrapper(applicationContext)
        val documentDirectory = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        val file = File(documentDirectory, "fac-"+userLastName+".pdf")
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

    fun sucessResuul(){
        FancyToast.makeText(
            this,
            "Se a realizado el cobro correctamente !",
            FancyToast.LENGTH_SHORT,
            FancyToast.SUCCESS,
            false
        ).show()
    }
}