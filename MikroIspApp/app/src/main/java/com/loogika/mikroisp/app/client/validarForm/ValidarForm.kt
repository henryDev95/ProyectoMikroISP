package com.loogika.mikroisp.app.client.validarForm

import android.util.Log
import com.loogika.mikroisp.app.databinding.ActivityNewClientBinding
import com.loogika.mikroisp.app.databinding.FragmentClientBinding

class ValidarForm(
      dni: String,
      firstName: String,
      lastName: String,
      direction: String,
      telephone: String,
      email: String,
      description: String,
      binding:ActivityNewClientBinding
) {
    var dni: String = ""
    var firstName: String = ""
    var lastName: String  = ""
    var direction: String  = ""
    var telephone: String  = ""
    var email: String  = ""
    var description: String = ""
    lateinit var binding: ActivityNewClientBinding

    init{
        this.dni = dni
        this.firstName = firstName
        this.lastName = lastName
        this.direction = direction
        this.telephone =telephone
        this.email=email
        this.description=description
        this.binding = binding
    }

    fun validarCedula():Boolean {
        if (dni.isEmpty()) {
            binding.dni.error = "El campo no puede estar vacio"
            return false
        } else {
            var valor = 0
            var suma = 0
            var cedula = IntArray(10)
            for (i in 0..9) {
                cedula[i] = Integer.parseInt(dni[i].toString())

            }
            var identificador = cedula[9]
            Log.d("iddentificador", identificador.toString())
            for (i in 0..8) {
                if (i % 2 == 0) {
                    valor = cedula[i] * 2
                } else {
                    valor = cedula[i] * 1
                }
                if (valor >= 10) {
                    valor = valor - 9

                }
                suma += valor
            }

                var ValorDecena = (10 - (suma % 10) % 10)

                Log.d("digito", ValorDecena.toString())

                var cedulaEs = false

                if (ValorDecena == identificador) {
                    binding.dni.error = "cedula correcta"
                    cedulaEs = true
                } else {
                    binding.dni.error = "cedula Incorrecta"
                }
                return cedulaEs

        }
    }




}