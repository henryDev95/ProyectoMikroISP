package com.loogika.mikroisp.app.payment.toast

import android.content.Context
import android.view.Gravity
import android.widget.Toast
import com.shashank.sony.fancytoastlib.FancyToast

object ImprimirResultado {

    fun error(context:Context) { // metodo para informar el error
        Toast.makeText(context, "!No se realizo la llamada al servidor!", Toast.LENGTH_SHORT).show()
    }

    fun resultNullContent(context:Context) {
        val toast = FancyToast.makeText(
            context,
            "!No hay datos de los clientes!",
            FancyToast.LENGTH_LONG,
            FancyToast.WARNING,
            false
        )
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    /////////////////// pdf respuesta //////////////////////////////////

    fun sucessResuulPdf(context:Context) {
        FancyToast.makeText(
            context,
            "Se genero el pdf correctamente!",
            FancyToast.LENGTH_SHORT,
            FancyToast.SUCCESS,
            false
        ).show()

    }

    //// respuesta de los cobros/////////////////////////////////


    fun successResultado(context: Context) {
        val toast = FancyToast.makeText(
            context,
            "Se a realizado correctamente el cobro!",
            FancyToast.LENGTH_SHORT,
            FancyToast.SUCCESS,
            false
        )
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    fun cancelarResultado(context: Context) {
        val toast = FancyToast.makeText(
            context,
            "No se realizo el cobro!",
            FancyToast.LENGTH_SHORT,
            FancyToast.WARNING,
            false
        )
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }


}