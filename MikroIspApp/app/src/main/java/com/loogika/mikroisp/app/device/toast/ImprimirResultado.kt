package com.loogika.mikroisp.app.device.toast

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.Toast
import com.loogika.mikroisp.app.device.DeviceActivity
import com.shashank.sony.fancytoastlib.FancyToast

object ImprimirResultado {

    fun ImprimirRespuestoLlamada(context: Context) {
        FancyToast.makeText(
            context,
            "!No se realizó la petición!",
            FancyToast.LENGTH_SHORT,
            FancyToast.WARNING,
            false
        ).show()
    }

    fun errorLlamada(context:Context) { // metodo para informar el error
        FancyToast.makeText(
            context,
            "!El servidor se encuentra fuera del servicio!",
            FancyToast.LENGTH_SHORT,
            FancyToast.WARNING,
            false
        ).show()
    }

    // resultado para editar el equipo//////////////////////////////////////////////////////////////////

    fun cancelarResultadoEdit(context:Context) {
        val toast = FancyToast.makeText(
            context,
            "No se editó los datos del equipo!",
            FancyToast.LENGTH_LONG,
            FancyToast.WARNING,
            false
        )
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    fun successResultadoEdit(context:Context) {
        val toast = FancyToast.makeText(
            context,
            "Se editó los datos del equipo correctamente!",
            FancyToast.LENGTH_LONG,
            FancyToast.SUCCESS,
            false
        )
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

}