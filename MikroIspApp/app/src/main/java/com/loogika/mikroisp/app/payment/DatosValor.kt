package com.loogika.mikroisp.app.payment

class DatosValor() {

    private var valorRes :Int = 100

    fun getValores():Int{
        return valorRes
    }

    fun setValor(valor:Int){
        this.valorRes = valor
    }

}