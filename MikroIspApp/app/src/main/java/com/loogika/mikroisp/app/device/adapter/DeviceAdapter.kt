package com.loogika.mikroisp.app.device.adapter

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.loogika.mikroisp.app.R
import com.loogika.mikroisp.app.client.entity.Client
import com.loogika.mikroisp.app.databinding.ItemClientBinding
import com.loogika.mikroisp.app.databinding.ItemDeviceBinding
import com.loogika.mikroisp.app.device.entity.Brand
import com.loogika.mikroisp.app.device.entity.Device
import com.loogika.mikroisp.app.device.entity.StatusDevice

class DeviceAdapter(val context:Context, val devices: List<Device>, val itemsClick: CellClickListener):RecyclerView.Adapter<DeviceAdapter.DeviceHolder>() {

    interface CellClickListener {
        fun onCellClickListener(id:Int, name:String , code: String, model:String, mac:String , brand:Brand, statusDevice:StatusDevice)

    }

    // Clase para refeenciar el diseño del item
    class DeviceHolder(val binding:ItemDeviceBinding , var itemsClick: CellClickListener , val con:Context) : RecyclerView.ViewHolder(binding.root) {  // hace referencia m al diseño de los items
        private var name: TextView = binding.name
        private var state: TextView = binding.stateDevice

        fun bind(device : Device) {
            name.text = "${device.name}  ${device.model}"
            state.text = device.brand.name

           /* binding.itemsClient.setOnClickListener {
                itemsClick.onCellClickListener( device.id,device.name,device.code,device.model,device.mac,device.brand,device.statusDevice)
            }
            */

            binding.icOption.setOnClickListener {
                val id = device.model.toString()
                menuOpcion(it,id)
            }
        }

        fun menuOpcion(view:View , id:String){
            val popup = PopupMenu(con.applicationContext, view)
            popup.inflate(R.menu.show_menu)
            popup.setOnMenuItemClickListener {
                 when(it.itemId){
                     R.id.edit ->{
                         val v = LayoutInflater.from(con).inflate(R.layout.edit_device,null)
                         AlertDialog.Builder(con)
                             .setView(v)
                             .setPositiveButton("Aceptar"){
                                     dialog,_->
                                 Toast.makeText(con,"!Información editada! $id",Toast.LENGTH_SHORT).show()
                                 dialog.dismiss()

                             }
                             .setNegativeButton("Cancel"){
                                     dialog,_->
                                 dialog.dismiss()

                             }
                             .create()
                             .show()
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
        return devices.size
    }

    // Displays data at a certain position
    override fun onBindViewHolder(holder: DeviceHolder, position: Int) { // devuelve los items
        var items = devices[position]
        holder.bind(items)
    }
}