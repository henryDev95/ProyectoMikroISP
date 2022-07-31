package com.loogika.mikroisp.app.device.adapter

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.loogika.mikroisp.app.R
import com.loogika.mikroisp.app.client.EditClientActivity
import com.loogika.mikroisp.app.client.entity.Client
import com.loogika.mikroisp.app.databinding.ActivityEditClientBinding
import com.loogika.mikroisp.app.databinding.ActivityShowDeviceBinding
import com.loogika.mikroisp.app.databinding.ItemClientBinding
import com.loogika.mikroisp.app.databinding.ItemDeviceBinding
import com.loogika.mikroisp.app.device.EditDeviceActivity
import com.loogika.mikroisp.app.device.entity.Brand
import com.loogika.mikroisp.app.device.entity.Device
import com.loogika.mikroisp.app.device.entity.StatusDevice

class DeviceAdapter(val context:Context, val devices: List<Device>, val itemsClick: CellClickListener):RecyclerView.Adapter<DeviceAdapter.DeviceHolder>(), Filterable {

    var filteredDeviceList:List<Device> = mutableListOf()

    init {
        this.filteredDeviceList = devices
    }


    interface CellClickListener {
        fun onCellClickListener(id:Int, name:String , code: String, model:String, mac:String , brand:Brand, statusDevice:StatusDevice)

    }

    // Clase para refeenciar el diseño del item
    class DeviceHolder(val binding:ItemDeviceBinding , var itemsClick: CellClickListener , val con:Context) : RecyclerView.ViewHolder(binding.root) {  // hace referencia m al diseño de los items
        private var name: TextView = binding.name
        private var state: TextView = binding.stateDevice

        fun bind(device : Device) {
            name.text = "${device.name}  ${device.model}"
            state.text = device.brand?.name

            binding.itemsClient.setOnClickListener {

               // itemsClick.onCellClickListener( device.id,device.name,device.code,device.model,device.mac,device.brand,device.statusDevice)
            }


            binding.icOption.setOnClickListener {
                  menuOpcion(it,device)
            }


        }

        fun menuOpcion(view:View , device:Device){
            val popup = PopupMenu(con.applicationContext, view)
            popup.inflate(R.menu.show_menu)
            popup.setOnMenuItemClickListener {
                 when(it.itemId){
                     R.id.show ->{
                         val v = LayoutInflater.from(con).inflate(R.layout.activity_show_device,null)
                         val name = v.findViewById<TextView>(R.id.name)
                         val code = v.findViewById<TextView>(R.id.code)
                         val model = v.findViewById<TextView>(R.id.model)
                         val mac = v.findViewById<TextView>(R.id.mac)
                         val isAssigng = v.findViewById<TextView>(R.id.isAssidned)
                         val brand = v.findViewById<TextView>(R.id.brand)
                         val status = v.findViewById<TextView>(R.id.statusDevice)
                         name.text = device.name
                         code.text = device.code
                         model.text = device.model
                         mac.text = device.mac
                         if(device.isAssigned){
                             isAssigng.text = "Activo"
                         }else{
                             isAssigng.text = "Cancelado"
                         }
                         brand.text = device.brand?.name
                         status.text = device.statusDevice!!.name

                         AlertDialog.Builder(con)
                             .setView(v)
                             .setNegativeButton("Cerrar"){
                                     dialog,_->
                                 dialog.dismiss()
                             }
                             .create()
                             .show()
                         true
                     }

                     R.id.edit->{
                         val intent = Intent(con,EditDeviceActivity::class.java)
                         intent.putExtra("device",device)
                         con.startActivity(intent)
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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceHolder {

        val binding_Items_device = ItemDeviceBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return DeviceHolder(binding_Items_device ,itemsClick, context)
    }

    // Returns size of data list
    override fun getItemCount(): Int { // devuelve la cantidad de los items
        return filteredDeviceList.size
    }

    // Displays data at a certain position
    override fun onBindViewHolder(holder: DeviceHolder, position: Int) { // devuelve los items
        var items = filteredDeviceList[position]
        holder.bind(items)
    }

    override fun getFilter(): Filter { // metodo para filtar la busqueda un valor
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    filteredDeviceList = devices
                } else {
                    val resultList = ArrayList<Device>()
                    for (row in devices) {
                        if (row.name.toLowerCase().contains(charSearch.lowercase())) {
                            resultList.add(row)
                        }
                    }
                    filteredDeviceList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filteredDeviceList
                return filterResults
            }
            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredDeviceList = results?.values as ArrayList<Device>
                notifyDataSetChanged()
            }

        }
    }
}