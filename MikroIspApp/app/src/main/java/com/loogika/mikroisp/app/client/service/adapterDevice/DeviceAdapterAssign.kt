package com.loogika.mikroisp.app.client.service.adapterDevice

import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.loogika.mikroisp.app.R
import com.loogika.mikroisp.app.client.EditClientActivity
import com.loogika.mikroisp.app.client.entity.Client
import com.loogika.mikroisp.app.databinding.*
import com.loogika.mikroisp.app.device.entity.Brand
import com.loogika.mikroisp.app.device.entity.Device
import com.loogika.mikroisp.app.device.entity.StatusDevice

class DeviceAdapterAssign(val context:Context, val devices: List<Device>, val itemsClick: CellClickListener):RecyclerView.Adapter<DeviceAdapterAssign.DeviceHolder>(), Filterable {

    var filteredDeviceList:List<Device> = mutableListOf()
    var lastSelectedPosition = -1;

    init {
        this.filteredDeviceList = devices
    }

    interface CellClickListener {
        fun onCellClickListener(id:Int)
    }

    class DeviceHolder(val binding:ItemDeviceAssignBinding , var itemsClick: CellClickListener , val con:Context) : RecyclerView.ViewHolder(binding.root) {  // hace referencia m al diseño de los items
        private var name: TextView = binding.name
        private var state: TextView = binding.stateDevice

        fun bind(device : Device) {
            name.text = "${device.name}"
            state.text = device.brand.name

            binding.itemsDevice.setOnClickListener {
                itemsClick.onCellClickListener(device.id)
                binding.textAceptado.isVisible = true

            }
        }

        private fun mostrarDialog(contex: Context) {
            val builder = androidx.appcompat.app.AlertDialog.Builder(contex)
            builder.setTitle("Seleccionar Equipo")
                .setMessage("Desea seleccionar equipo?")
                .setPositiveButton(R.string.accept,
                    DialogInterface.OnClickListener { dialog, id ->
                        Toast.makeText(con, "Seleccionado", Toast.LENGTH_SHORT).show()

                    })
                .setNegativeButton(R.string.cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                        Toast.makeText(con, "No Seleccionado", Toast.LENGTH_SHORT).show()


                    })
            builder.show()
        }
    }


    // Returns a new ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceHolder {

        val binding_Items_device = ItemDeviceAssignBinding.inflate(LayoutInflater.from(parent.context), parent,false)
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