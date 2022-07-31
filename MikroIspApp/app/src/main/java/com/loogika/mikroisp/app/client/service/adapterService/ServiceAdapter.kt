package com.loogika.mikroisp.app.client.service.adapterService

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
import com.loogika.mikroisp.app.client.service.entity.ServiceDevice
import com.loogika.mikroisp.app.databinding.*
import com.loogika.mikroisp.app.device.entity.Brand
import com.loogika.mikroisp.app.device.entity.Device
import com.loogika.mikroisp.app.device.entity.StatusDevice
import com.loogika.mikroisp.app.payment.entity.Plan
import com.loogika.mikroisp.app.payment.entity.Service

class ServiceAdapter(val context:Context, val service: List<Service>, val itemsClick: CellClickListener):RecyclerView.Adapter<ServiceAdapter.DeviceHolder>(){

    interface CellClickListener {
        fun onCellClickListener(id:Int , position: Int)
    }

    class DeviceHolder(val binding:ItemServiceListBinding , var itemsClick: CellClickListener , val con:Context) : RecyclerView.ViewHolder(binding.root) {  // hace referencia m al diseño de los items
        private var name: TextView = binding.name
        private var state: TextView = binding.stateDevice

        fun bind(service : Service , position: Int) {
            name.text = "${service.name}"
            if(service.status == 1){
                state.text = "Activo"
            }else{
                state.text = "Desabilitado"
            }

            binding.itemsService.setOnClickListener {
                itemsClick.onCellClickListener(service.id , position)

            }
        }
    }
    // Returns a new ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceHolder {
        val binding_Items_service = ItemServiceListBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return DeviceHolder(binding_Items_service ,itemsClick, context)
    }

    // Returns size of data list
    override fun getItemCount(): Int { // devuelve la cantidad de los items
        return service.size
    }

    // Displays data at a certain position
    override fun onBindViewHolder(holder: DeviceHolder, position: Int) { // devuelve los items
        var items = service[position]
        holder.bind(items, position)
    }


}