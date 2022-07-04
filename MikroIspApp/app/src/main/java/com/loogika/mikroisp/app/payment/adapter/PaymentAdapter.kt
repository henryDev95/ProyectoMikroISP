package com.loogika.mikroisp.app.payment.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.loogika.mikroisp.app.client.entity.Client
import com.loogika.mikroisp.app.databinding.ItemClientBinding
import com.loogika.mikroisp.app.databinding.ItemServiceClientBinding
import com.loogika.mikroisp.app.payment.entity.Payment
import com.loogika.mikroisp.app.payment.entity.Plan


class PaymentAdapter(val clientsService: List<Payment>, val itemsClick: CellClickListener):RecyclerView.Adapter<PaymentAdapter.ClientHolder>(),Filterable {

    var filteredClientList:List<Payment> = mutableListOf()

    init {
        this.filteredClientList = clientsService
    }

    interface CellClickListener {
        fun onCellClickListener(idInvoice:Int,numberInvoice:String,total:Float, id:Int,type:Int,dni:String, userFirstName:String , userLastName : String, address:String, telephone:String, plan:Plan)

    }

    // Clase para refeenciar el diseño del item
    class ClientHolder(val binding:ItemServiceClientBinding , var itemsClick: CellClickListener) : RecyclerView.ViewHolder(binding.root) {  // hace referencia m al diseño de los items
        private var name: TextView = binding.name
        fun bind(payment:Payment) {
            name.text = "${payment.client.userFirstName} ${payment.client.userLastName}"
            binding.cobrar.setOnClickListener {
                itemsClick.onCellClickListener( payment.id,payment.number.toString(), payment.total,payment.client.id,payment.client.type,payment.client.dni,payment.client.userFirstName.toString(),payment.client.userLastName.toString(),payment.client.address.toString(),payment.client.phone1.toString() , payment.client.services[0].plan)
            }
        }
    }

    // Returns a new ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientHolder {

        val binding_Items_service_client = ItemServiceClientBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ClientHolder(binding_Items_service_client ,itemsClick)
    }

    // Returns size of data list
    override fun getItemCount(): Int { // devuelve la cantidad de los items
        return filteredClientList.size
    }

    // Displays data at a certain position
    override fun onBindViewHolder(holder: ClientHolder, position: Int) { // devuelve los items
        var items = filteredClientList[position]
        holder.bind(items)
    }

    override fun getFilter(): Filter {
        return object :Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    filteredClientList = clientsService
                } else {
                    val resultList = ArrayList<Payment>()
                    clientsService.forEach { row->

                        if (row.client.userFirstName?.toLowerCase().toString().contains(charSearch.lowercase())) {
                            resultList.add(row)

                        }
                    }
                    filteredClientList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filteredClientList
                return filterResults
            }
            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredClientList = results?.values as ArrayList<Payment>
                notifyDataSetChanged()
            }
        }
    }
}