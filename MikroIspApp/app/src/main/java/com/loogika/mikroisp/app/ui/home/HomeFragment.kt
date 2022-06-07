package com.loogika.mikroisp.app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.loogika.mikroisp.app.databinding.FragmentHomeBinding
import com.loogika.mikroisp.app.payment.ShowServiceActivity


class HomeFragment : Fragment() {

       private lateinit var binding: FragmentHomeBinding


       override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

           var cantidadCobro = ShowServiceActivity()
           var valor = cantidadCobro.catidadPagos

           binding.cantidadPayment.text = valor.toString()

        return root
    }


}