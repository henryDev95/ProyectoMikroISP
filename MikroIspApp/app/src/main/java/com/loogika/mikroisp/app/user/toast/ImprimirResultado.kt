package com.loogika.mikroisp.app.user.toast

import android.content.Context
import android.view.Gravity
import com.shashank.sony.fancytoastlib.FancyToast

object ImprimirResultado {

    fun warningResulLogin(context : Context) {
        FancyToast.makeText(
            context,
            "!Acceso Denegado!",
            FancyToast.LENGTH_SHORT,
            FancyToast.WARNING,
            false
        ).show()
    }

    fun errrorResulNetword(context : Context) {
        val toast = FancyToast.makeText(
            context,
            "!No hay internet!",
            FancyToast.LENGTH_SHORT,
            FancyToast.WARNING,
            false
        )
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }



}