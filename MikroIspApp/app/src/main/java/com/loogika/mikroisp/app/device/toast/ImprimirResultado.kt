package com.loogika.mikroisp.app.device.toast

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.Toast
import com.loogika.mikroisp.app.device.DeviceActivity
import com.shashank.sony.fancytoastlib.FancyToast

object ImprimirResultado {

    fun ImprimirRespuestoLlamada(context: Context) {
        Toast.makeText(context, "No hay datos del equipo", Toast.LENGTH_SHORT).show()
    }

    fun errorLlamada(context:Context) { // metodo para informar el error
        Toast.makeText(context, "No se realizo la llamada", Toast.LENGTH_SHORT).show()
    }

    // resultado para editar el equipo//////////////////////////////////////////////////////////////////

    fun cancelarResultadoEdit(context:Context) {
        val toast = FancyToast.makeText(
            context,
            "No se edito los datos del equipo!",
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
            "Se edito los datos del equipo correctamente!",
            FancyToast.LENGTH_LONG,
            FancyToast.SUCCESS,
            false
        )
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

}