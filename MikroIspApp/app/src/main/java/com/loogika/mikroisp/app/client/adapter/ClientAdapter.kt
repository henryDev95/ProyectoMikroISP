package com.loogika.mikroisp.app.client.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.loogika.mikroisp.app.client.entity.Client
import com.loogika.mikroisp.app.databinding.ItemClientBinding

class ClientAdapter(val clients: List<Client> , val itemsClick: CellClickListener):RecyclerView.Adapter<ClientAdapter.ClientHolder>() {

    interface CellClickListener {
        fun onCellClickListener(dni:String, userFirstName:String , userLastName : String, address:String,country:String , telephone:String)

    }

    // Clase para refeenciar el diseño del item
    class ClientHolder(val binding:ItemClientBinding , var itemsClick: CellClickListener) : RecyclerView.ViewHolder(binding.root) {  // hace referencia m al diseño de los items
        private var name: TextView = binding.name
        private var dni: TextView = binding.dni

        fun bind(client : Client) {
            name.text = "${client.userFirstName} ${client.userLastName}"
            dni.text = client.dni
            binding.itemsClient.setOnClickListener {
                itemsClick.onCellClickListener( client.dni,client.userFirstName,client.userLastName,client.address, client.country , client.phone1)
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
        return clients.size
    }

    // Displays data at a certain position
    override fun onBindViewHolder(holder: ClientHolder, position: Int) { // devuelve los items
        var items = clients[position]
        holder.bind(items)
    }
}