package com.loogika.mikroisp.app.report.toast

import android.content.Context
import android.view.Gravity
import com.shashank.sony.fancytoastlib.FancyToast

object ShowResult {

    fun sucessResuulPdf(context:Context){
        val toast = FancyToast.makeText(
            context,
            "Se genero el pdf correctamente!",
            FancyToast.LENGTH_SHORT,
            FancyToast.SUCCESS,
            false
        )
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()

    }

    fun errorResuulDate(context:Context){
        val toast = FancyToast.makeText(
            context,
            "No se permite la fecha seleccionada",
            FancyToast.LENGTH_LONG,
            FancyToast.ERROR,
            false
        )
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()

    }

    fun sucessResultList(context:Context){
        val toast = FancyToast.makeText(
            context,
            "!No hay cobros realizados!",
            FancyToast.LENGTH_LONG,
            FancyToast.WARNING,
            false
        )
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }
}