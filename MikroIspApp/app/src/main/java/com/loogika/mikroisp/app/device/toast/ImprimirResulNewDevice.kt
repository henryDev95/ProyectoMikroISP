package com.loogika.mikroisp.app.device.toast

import android.content.Context
import android.view.Gravity
import android.widget.Toast
import com.loogika.mikroisp.app.device.NewDeviceActivity
import com.shashank.sony.fancytoastlib.FancyToast

object ImprimirResulNewDevice {

    fun warningResult(context: Context) {
        val toast = FancyToast.makeText(
            context,
            "!Existe un registro con el mismo código!",
            FancyToast.LENGTH_SHORT,
            FancyToast.WARNING,
            false
        )
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    fun successResult(context:Context) {
        val toast = FancyToast.makeText(
            context,
            "!Se ha ingresado correctamente el equipo!",
            FancyToast.LENGTH_SHORT,
            FancyToast.SUCCESS,
            false
        )
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    fun warningNewDevice(context: Context) {
        val toast=FancyToast.makeText(
            context,
            "!No se realizó el registro del equipo!",
            FancyToast.LENGTH_SHORT,
            FancyToast.WARNING,
            false
        )
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    fun errorLlamada(context:Context) { // metodo para informar el error
        Toast.makeText(context, "No se realizó la llamada", Toast.LENGTH_SHORT).show()
    }
}