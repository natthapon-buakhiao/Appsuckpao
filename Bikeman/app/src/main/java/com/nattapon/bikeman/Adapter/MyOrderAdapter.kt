package com.nattapon.bikeman.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import com.google.firebase.database.FirebaseDatabase
import com.nattapon.bikeman.Data.Order
import com.nattapon.bikeman.R

class MyOrderAdapter(val mCtx: Context, val layoutResId:Int, val OrderList:List<Order>)
    : ArrayAdapter<Order>(mCtx,layoutResId,OrderList)  {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val showName = view.findViewById<TextView>(R.id.showmyName)
        val showNum = view.findViewById<TextView>(R.id.showmyNum)
        val showLaundry = view.findViewById<TextView>(R.id.showmyLaundry)
        val showStatus = view.findViewById<TextView>(R.id.showmyStatus)
        val showPlace = view.findViewById<TextView>(R.id.showmyplace)
        val showDetail = view.findViewById<TextView>(R.id.showmydetail)

        val showNamec = view.findViewById<TextView>(R.id.mystore)
        val showNumc = view.findViewById<TextView>(R.id.mynum)
        val showLaundryc = view.findViewById<TextView>(R.id.mylaundry)
        val showStatusc = view.findViewById<TextView>(R.id.mystatus)
        val place = view.findViewById<TextView>(R.id.myreplace)
        val deatail = view.findViewById<TextView>(R.id.mydetail)

        val updateOrder = view.findViewById<Button>(R.id.updateMyorder)
        val updateCost = view.findViewById<Button>(R.id.updateCost)

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

        updateOrder.setOnClickListener {
            updateMyOrder(order)

        }
        updateCost.setOnClickListener {
            Cost(order)
        }


        return view
    }
    private fun Cost(order: Order){
        var uprice = 40
        var newprice = 0
        newprice =uprice + order.price.toInt()
        val ref = FirebaseDatabase.getInstance().getReference("acceptOrders")
        val border = Order(order.orderid, order.num, order.laundry, order.store, order.status, order.userid
            ,order.detail,newprice.toString(),order.bikeId,order.date,order.storeid,order.latitude,order.longitude)

        ref.child(order.bikeId).child(order.orderid).setValue(border)


    }

    private fun updateMyOrder(order: Order) {
        val newStatus:String = "finish"
        val ref = FirebaseDatabase.getInstance().getReference("acceptOrders")
        val border = Order(order.orderid, order.num, order.laundry, order.store,newStatus, order.userid
            ,order.detail,order.price,order.bikeId,order.date,order.storeid,order.latitude,order.longitude)

        ref.child(order.bikeId).child(order.orderid).setValue(border)


    }
}