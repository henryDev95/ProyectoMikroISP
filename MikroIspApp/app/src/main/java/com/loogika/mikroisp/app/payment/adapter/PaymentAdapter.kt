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
import com.loogika.mikroisp.app.payment.entity.Plan


class PaymentAdapter(val clientsService: List<Client>, val itemsClick: CellClickListener):RecyclerView.Adapter<PaymentAdapter.ClientHolder>(),Filterable {

    var filteredClientList:List<Client> = mutableListOf()

    init {
        this.filteredClientList = clientsService
    }

    interface CellClickListener {
        fun onCellClickListener(id:Int,type:Int,dni:String, userFirstName:String , userLastName : String, address:String, telephone:String, plan:Plan)

    }

    // Clase para refeenciar el diseño del item
    class ClientHolder(val binding:ItemServiceClientBinding , var itemsClick: CellClickListener) : RecyclerView.ViewHolder(binding.root) {  // hace referencia m al diseño de los items
        private var name: TextView = binding.name
        private var dni: TextView = binding.dni

        fun bind(client : Client) {
            name.text = "${client.userFirstName} ${client.userLastName}"
            dni.text = client.dni
            binding.cobrar.setOnClickListener {
                itemsClick.onCellClickListener( client.id,client.type,client.dni,client.userFirstName.toString(),client.userLastName.toString(),client.address,client.phone1 , client.services[0].plan)
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
                    val resultList = ArrayList<Client>()
                    clientsService.forEach { row->
                        Log.d("encontrado",row.userFirstName?.toLowerCase().toString())
                        if (row.userFirstName?.toLowerCase().toString().contains(charSearch.lowercase())) {
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
                filteredClientList = results?.values as ArrayList<Client>
                notifyDataSetChanged()
            }
        }
    }
}