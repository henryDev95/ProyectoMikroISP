package com.loogika.mikroisp.app.client.toast

import android.content.Context
import android.view.Gravity
import android.widget.Toast
import com.shashank.sony.fancytoastlib.FancyToast

object ImprimirResultado {

    fun ImprimirRespuesta(context: Context) {
        FancyToast.makeText(
            context,
            "!No se realizo la petición!",
            FancyToast.LENGTH_SHORT,
            FancyToast.WARNING,
            false
        ).show()

    }

    fun error(context: Context) { // metodo para informar el error
        FancyToast.makeText(
            context,
            "!El servidor se encuentra fuera d servicio!",
            FancyToast.LENGTH_SHORT,
            FancyToast.WARNING,
            false
        ).show()
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


    fun successResultadoServiceEdit(context: Context) {
        val toast = FancyToast.makeText(
            context,
            "Información editada correctamente!",
            FancyToast.LENGTH_SHORT,
            FancyToast.SUCCESS,
            false
        )
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    fun cancelarResultadoServiceEdit(context: Context) {
        val toast = FancyToast.makeText(
            context,
            "No se editó la información del servicio!",
            FancyToast.LENGTH_SHORT,
            FancyToast.WARNING,
            false
        )
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }


    //////////////// Nuevo cliente


    fun validarCedulaExistente(context: Context) { // metodo para informar el error
        val toast = FancyToast.makeText(
            context,
            "!Existe un cliente con número de cédula ingresada!",
            FancyToast.LENGTH_SHORT,
            FancyToast.WARNING,
            false
        )
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }


    /// editar cliente //


    fun successResultadoClientEdit(context: Context) {
        val toast = FancyToast.makeText(
            context,
            "Información editada correctamente!",
            FancyToast.LENGTH_SHORT,
            FancyToast.SUCCESS,
            false
        )
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    fun cancelarResultadoClientEdit(context: Context) {
        val toast = FancyToast.makeText(
            context,
            "No se editó los datos del cliente!",
            FancyToast.LENGTH_LONG,
            FancyToast.WARNING,
            false
        )
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }


}