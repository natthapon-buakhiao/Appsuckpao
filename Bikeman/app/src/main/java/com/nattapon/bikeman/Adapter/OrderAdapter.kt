package com.nattapon.bikeman.Adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.nattapon.bikeman.Data.Order
import com.nattapon.bikeman.Data.NewOrder
import com.nattapon.bikeman.Map
import com.nattapon.bikeman.R


class OrderAdapter (val mCtx:Context,val layoutResId:Int,val OrderList:List<NewOrder>)
    :ArrayAdapter<NewOrder>(mCtx,layoutResId,OrderList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId, null)


        val showName = view.findViewById<TextView>(R.id.showName)
        val showNum = view.findViewById<TextView>(R.id.showNum)
        val showLaundry = view.findViewById<TextView>(R.id.showLaundry)
        val showStatus = view.findViewById<TextView>(R.id.showStatus)
        val showPlace = view.findViewById<TextView>(R.id.showplace)
        val showDetail = view.findViewById<TextView>(R.id.showdetail)

        val showNamec = view.findViewById<TextView>(R.id.storec)
        val showNumc = view.findViewById<TextView>(R.id.numc)
        val showLaundryc = view.findViewById<TextView>(R.id.laundryc)
        val showStatusc = view.findViewById<TextView>(R.id.statusc)
        val place = view.findViewById<TextView>(R.id.replace)
        val deatail = view.findViewById<TextView>(R.id.detailc)

        val update_status = view.findViewById<Button>(R.id.status_btn)
        val updateProcess = view.findViewById<Button>(R.id.updateProcess)
        val save_order = view.findViewById<Button>(R.id.saveOder_btn)
        val btnMab = view.findViewById<Button>(R.id.btnMap)

        var numx: String = "จำนวน :"
        val laundryx: String = "รีด/ไม่รีด :"
        val storex: String = "ร้าน :"
        val statusx: String = "สถานะ :"
        val placex:String="ราคา :"
        val detailx:String="รานละเอียด :"


        val order = OrderList[position]

        showNum.text = order.num
        showLaundry.text = order.laundry
        showName.text = order.store
        showStatus.text = order.status
        showPlace.text = order.price
        showDetail.text = order.detail


        showNamec.text = storex
        showNumc.text = numx
        showLaundryc.text = laundryx
        showStatusc.text = statusx
        place.text = placex
        deatail.text = detailx

        update_status.setOnClickListener {
            updateSatus(order)
        }
        updateProcess.setOnClickListener {
            statusProcess(order)
        }
        save_order.setOnClickListener {
            saveOder(order)
        }
        btnMab.setOnClickListener {

            val intent= Intent(view.context, Map::class.java)
            intent.putExtra("latitude", order.latitude)
            intent.putExtra("longitude", order.longitude)
            view.context.startActivity(intent)

        }



        return view
    }


    private fun saveOder(order:NewOrder){
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("acceptOrders/$uid")
        val orderIn = Order(order.orderid, order.num, order.laundry, order.store, order.status, order.userid
            ,order.detail,order.price,uid,order.date,order.storeid,order.latitude,order.longitude)

        ref.child(order.orderid).setValue(orderIn)

    }
    private fun  statusProcess(order: NewOrder){
        val newStatus:String = "In progress"
        val ref = FirebaseDatabase.getInstance().getReference("order")
        val border = NewOrder(order.orderid, order.num, order.laundry, order.store, newStatus, order.userid
            ,order.detail,order.price,order.date,order.storeid,order.latitude,order.longitude)

        ref.child(order.userid).child(order.orderid).setValue(border)
    }

    private fun updateSatus(order: NewOrder) {

                val newStatus:String = "finish"
                val ref = FirebaseDatabase.getInstance().getReference("order")
                val border = NewOrder(order.orderid, order.num, order.laundry, order.store, newStatus, order.userid
                ,order.detail,order.price,order.date,order.storeid,order.latitude,order.longitude)

                ref.child(order.userid).child(order.orderid).setValue(border)

    }
}





