package com.loogika.mikroisp.app.client.toast

import android.content.Context
import android.view.Gravity
import android.widget.Toast
import com.shashank.sony.fancytoastlib.FancyToast

object ImprimirResultado {

    fun ImprimirRespuesta(context: Context) {
        Toast.makeText(context, "No se realizo la llamada al servidor ", Toast.LENGTH_SHORT).show()
    }

    fun error(context: Context) { // metodo para informar el error
        Toast.makeText(context, "No se realizo la petición", Toast.LENGTH_SHORT).show()
    }


    fun successResultado(context: Context) {
        val toast = FancyToast.makeText(
            context,
            "Se registro el cliente correctamente!",
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
            "No se realizo el registro del cliente!",
            FancyToast.LENGTH_LONG,
            FancyToast.WARNING,
            false
        )
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    /// imprimir resultado de la asignacion del servicio con el dispositivo

    fun imprimirRespuestaEncontrada(context: Context) {
        Toast.makeText(context, "No hay datos de los equipos ", Toast.LENGTH_SHORT).show()
    }

    fun cancelarResult(context: Context) {
        val toast = FancyToast.makeText(
            context,
            "No se realizo el registro del servicio con el equipo!",
            FancyToast.LENGTH_SHORT,
            FancyToast.WARNING,
            false
        )
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    fun successResultadoAntena(context: Context) {
        val toast = FancyToast.makeText(
            context,
            "Se registro la antena correctamente!",
            FancyToast.LENGTH_SHORT,
            FancyToast.SUCCESS,
            false
        )
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    fun successResultadoRouter(context: Context) {
        val toast = FancyToast.makeText(
            context,
            "Se registro el router correctamente!",
            FancyToast.LENGTH_SHORT,
            FancyToast.SUCCESS,
            false
        )
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    //// Imprimir los resltados de nuevo servicio//////////////////////////////

    fun cancelarResultadoService(context: Context) {
        val toast = FancyToast.makeText(
            context,
            "No se realizo el registro del servicio!",
            FancyToast.LENGTH_SHORT,
            FancyToast.WARNING,
            false
        )
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    fun successResultadoService(context: Context) {
        val toast = FancyToast.makeText(
            context,
            "Se registro el servicio correctamente!",
            FancyToast.LENGTH_SHORT,
            FancyToast.SUCCESS,
            false
        )
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }



}