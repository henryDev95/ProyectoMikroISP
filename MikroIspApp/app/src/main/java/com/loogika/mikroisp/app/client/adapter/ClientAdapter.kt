package com.loogika.mikroisp.app.client.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.loogika.mikroisp.app.R
import com.loogika.mikroisp.app.client.EditClientActivity
import com.loogika.mikroisp.app.client.ShowClientActivity
import com.loogika.mikroisp.app.client.entity.Client
import com.loogika.mikroisp.app.databinding.ItemClientBinding
import com.loogika.mikroisp.app.device.entity.Device
import com.loogika.mikroisp.app.payment.entity.Plan
import com.loogika.mikroisp.app.payment.entity.Service

class ClientAdapter(val context:Context, val clients: List<Client> , val itemsClick: CellClickListener):RecyclerView.Adapter<ClientAdapter.ClientHolder>(), Filterable {

    var filteredClientList:List<Client> = mutableListOf()

    init {
        this.filteredClientList = clients
    }


    interface CellClickListener {
        fun onCellClickListener( id: Int, type:Int,dni:String, userFirstName:String , userLastName : String, address:String,telephone:String, service:Service, plan: Plan, city:String)

    }
    // Clase para refeenciar el diseño del item
    class ClientHolder(val binding:ItemClientBinding , var itemsClick: CellClickListener ,val context: Context) : RecyclerView.ViewHolder(binding.root) {  // hace referencia m al diseño de los items
        private var name: TextView = binding.userFirstName
        private var dni: TextView = binding.dni

        fun bind(client : Client) {
            name.text = "${client.userFirstName}  ${client.userLastName}"
            dni.text = client.dni
           /*
            binding.itemsClient.setOnClickListener {
                itemsClick.onCellClickListener( client.id ,client.type ,client.dni,client.userFirstName.toString(),client.userLastName.toString(),client.address, client.phone1 ,client.services[0],client.services[0].plan, client.city)
            }
            
            */

            binding.btOpcion.setOnClickListener {
                menuOpcion(it,client)
            }
        }

        fun menuOpcion(view: View, client:Client){
            val popup = PopupMenu(context.applicationContext, view)
            popup.inflate(R.menu.show_client)
            popup.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.show ->{
                        val intent  = Intent(context, ShowClientActivity::class.java)
                        intent.putExtra("client",client)
                        context.startActivity(intent)
                        true
                    }

                    R.id.edit->{
                        val intent = Intent(context, EditClientActivity::class.java)
                        intent.putExtra("client",client)
                        context.startActivity(intent)
                          true
                    }
                    else ->true

                }
            }
            popup.show()
            try {
                val fieldMPopup = PopupMenu::class.java.getDeclaredField("mPopup")
                fieldMPopup.isAccessible = true
                val mPopup = fieldMPopup.get(popup)
                mPopup.javaClass
                    .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                    .invoke(mPopup, true)
            } catch (e: Exception){
                Log.d("Main", "Error showing menu icons.", e)
            } finally {
                popup.show()
            }
        }
    }
    // Returns a new ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientHolder {

        val binding_Items_client = ItemClientBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ClientHolder(binding_Items_client ,itemsClick,context)
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
                notifyDataSetChanged()
           }
        }
    }
}