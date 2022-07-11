package com.loogika.mikroisp.app.report.toast

import android.content.Context
import com.shashank.sony.fancytoastlib.FancyToast

object ShowResult {

    fun sucessResuulPdf(context:Context){
        FancyToast.makeText(
            context,
            "Se genero el pdf correctamente!",
            FancyToast.LENGTH_SHORT,
            FancyToast.SUCCESS,
            false
        ).show()

    }

    fun errorResuulDate(context:Context){
        FancyToast.makeText(
            context,
            "No se permite la fecha seleccionada",
            FancyToast.LENGTH_LONG,
            FancyToast.ERROR,
            false
        ).show()

    }

    fun sucessResultList(context:Context){
        FancyToast.makeText(
            context,
            "No hay cobros realizados!",
            FancyToast.LENGTH_SHORT,
            FancyToast.WARNING,
            false
        ).show()

    }
}