package com.loogika.mikroisp.app.client.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.loogika.mikroisp.app.client.entity.Client
import com.loogika.mikroisp.app.databinding.ItemClientBinding
import com.loogika.mikroisp.app.device.entity.Device
import com.loogika.mikroisp.app.payment.entity.Plan

class ClientAdapter(val clients: List<Client> , val itemsClick: CellClickListener):RecyclerView.Adapter<ClientAdapter.ClientHolder>(), Filterable {

    var filteredClientList:List<Client> = mutableListOf()

    init {
        this.filteredClientList = clients
    }


    interface CellClickListener {
        fun onCellClickListener( id: Int, dni:String, userFirstName:String , userLastName : String, address:String,country:String , telephone:String, plan: Plan)

    }
    // Clase para refeenciar el diseño del item
    class ClientHolder(val binding:ItemClientBinding , var itemsClick: CellClickListener) : RecyclerView.ViewHolder(binding.root) {  // hace referencia m al diseño de los items
        private var name: TextView = binding.userFirstName
        private var dni: TextView = binding.dni

        fun bind(client : Client) {
            name.text = "${client.userFirstName}  ${client.userLastName}"
            dni.text = client.dni
            binding.itemsClient.setOnClickListener {
                itemsClick.onCellClickListener( client.id , client.dni,client.userFirstName.toString(),client.userLastName.toString(),client.address, client.country , client.phone1 , client.services[0].plan)
            }
           /*
            binding.icOption.setOnClickListener {
                Toast.makeText(it.context, "clicn obpcion", Toast.LENGTH_SHORT).show()
            }
             */
        }
    }
    // Returns a new ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientHolder {

        val binding_Items_client = ItemClientBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ClientHolder(binding_Items_client ,itemsClick)
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
                    filteredClientList = clients
                } else {
                    val resultList = ArrayList<Client>()
                    clients.forEach { row->
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
                Log.d("valores de busqyed", results?.values.toString())
               notifyDataSetChanged()
           }
        }
    }
}