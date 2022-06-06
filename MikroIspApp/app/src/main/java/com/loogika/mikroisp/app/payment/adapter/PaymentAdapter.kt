package com.loogika.mikroisp.app.payment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.loogika.mikroisp.app.client.entity.Client
import com.loogika.mikroisp.app.databinding.ItemClientBinding
import com.loogika.mikroisp.app.databinding.ItemServiceClientBinding
import com.loogika.mikroisp.app.payment.entity.Plan
import com.loogika.mikroisp.app.payment.entity.ServiceClient

class PaymentAdapter(val clientsService: List<ServiceClient>, val itemsClick: CellClickListener):RecyclerView.Adapter<PaymentAdapter.ClientHolder>() {

    interface CellClickListener {
        fun onCellClickListener(dni:String, userFirstName:String , userLastName : String, address:String,country:String , telephone:String, plan:Plan)

    }

    // Clase para refeenciar el diseño del item
    class ClientHolder(val binding:ItemServiceClientBinding , var itemsClick: CellClickListener) : RecyclerView.ViewHolder(binding.root) {  // hace referencia m al diseño de los items
        private var name: TextView = binding.name
        private var dni: TextView = binding.dni

        fun bind(client : ServiceClient) {
            name.text = "${client.userFirstName} ${client.userLastName}"
            dni.text = client.dni
            binding.itemsClient.setOnClickListener {
                itemsClick.onCellClickListener( client.dni,client.userFirstName,client.userLastName,client.address, client.country , client.phone1 , client.services[0].plan)
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
        return clientsService.size
    }

    // Displays data at a certain position
    override fun onBindViewHolder(holder: ClientHolder, position: Int) { // devuelve los items
        var items = clientsService[position]
        holder.bind(items)
    }
}