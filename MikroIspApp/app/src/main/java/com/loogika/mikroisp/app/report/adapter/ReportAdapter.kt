package com.loogika.mikroisp.app.report.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.loogika.mikroisp.app.client.entity.Client
import com.loogika.mikroisp.app.databinding.ItemClientBinding
import com.loogika.mikroisp.app.databinding.ItemPaymentListBinding
import com.loogika.mikroisp.app.databinding.ItemServiceClientBinding
import com.loogika.mikroisp.app.payment.entity.Payment
import com.loogika.mikroisp.app.payment.entity.Plan
import com.loogika.mikroisp.app.report.entity.Report


class ReportAdapter(val paymentList: List<Report>):RecyclerView.Adapter<ReportAdapter.ClientHolder>(),Filterable {

    var filteredPaymentList:List<Report> = mutableListOf()

    init {
        this.filteredPaymentList = paymentList
    }

    // Clase para refeenciar el diseño del item
    class ClientHolder(val binding:ItemPaymentListBinding) : RecyclerView.ViewHolder(binding.root) {  // hace referencia m al diseño de los items
        private var name: TextView = binding.name
        private var cedula:TextView = binding.dni
        fun bind(report:Report) {
            name.text = "${report.invoice.client.userFirstName} ${report.invoice.client.userLastName}"
            cedula.text = report.invoice.client.dni.toString()
        }
    }

    // Returns a new ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientHolder {

        val binding_Items_service_client = ItemPaymentListBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ClientHolder(binding_Items_service_client)
    }

    // Returns size of data list
    override fun getItemCount(): Int { // devuelve la cantidad de los items
        return paymentList.size
    }

    // Displays data at a certain position
    override fun onBindViewHolder(holder: ClientHolder, position: Int) { // devuelve los items
        var items = paymentList[position]
        holder.bind(items)
    }

    override fun getFilter(): Filter {
        return object :Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    filteredPaymentList = paymentList
                } else {
                    val resultList = ArrayList<Report>()
                    paymentList.forEach { row->

                        if (row.invoice.client.userFirstName?.toLowerCase().toString().contains(charSearch.lowercase())) {
                            resultList.add(row)

                        }
                    }
                    filteredPaymentList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filteredPaymentList
                return filterResults
            }
            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredPaymentList = results?.values as ArrayList<Report>
                notifyDataSetChanged()
            }
        }
    }
}