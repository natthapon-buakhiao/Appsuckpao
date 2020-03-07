package com.nattapon.bikeman

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.nattapon.bikeman.Adapter.MyOrderAdapter
import com.nattapon.bikeman.Adapter.OrderAdapter
import com.nattapon.bikeman.Data.Order
import kotlinx.android.synthetic.main.activity_bill.*

class Bill : AppCompatActivity() {

    private var database = FirebaseDatabase.getInstance()
    private var myRef = database.reference
    private var mAuth: FirebaseAuth? = null
    lateinit var OrderList:MutableList<Order>
    lateinit var ref: DatabaseReference
    lateinit var listView: ListView
    var count :Int =0
    var price :Double =10.00
    var places:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bill)

         OrderList= mutableListOf()

         val uid = FirebaseAuth.getInstance().uid ?: ""

         ref = FirebaseDatabase.getInstance().getReference("acceptOrders/$uid")
         listView = findViewById(R.id.billList)

         ref.addListenerForSingleValueEvent(object : ValueEventListener {
             override fun onCancelled(p0: DatabaseError) {

             }

             override fun onDataChange(p0: DataSnapshot) {

                 if(p0.exists()){


                     val children =p0.children
                     children.forEach{
                         println(it.toString())
                         count+=1
                         OrderList.add(it.getValue(Order::class.java)!!)

                     }


                     val adapter = MyOrderAdapter(
                         this@Bill,
                         R.layout.myorders,OrderList
                     )

                     listView.adapter=adapter


                 }
                 Income_btn.setOnClickListener {
                     createIncome()
                 }
             }

         })
    }

    private fun CrateBuill(count: Int, price: Double): Double {
        return count*price

    }
    private  fun createIncome(){
        val bill =CrateBuill(count,price)
        val bath = "บาท"
        val num = "จำนวน:"
        val text = "รวมเป็นเงิน"
        val showNum = count

        val showBill = findViewById<TextView>(R.id.billtextView)
        val showBath = findViewById<TextView>(R.id.bathtextView)
        val numCount = findViewById<TextView>(R.id.counttextView)
        val shownumCount =findViewById<TextView>(R.id.showCounttextView)
        val textshow =findViewById<TextView>(R.id.textView)
        showBill.text = bill.toString()
        showBath.text=bath
        numCount.text=num
        shownumCount.text=showNum.toString()
        textshow.text=text

        System.out.println ("price = "+ bill +" Bath ")

    }
    ////menu
    override fun onOptionsItemSelected(nav: MenuItem?): Boolean {
        when (nav?.itemId) {
            R.id.menu_new_order -> {
                val intent= Intent(this,MainActivity::class.java)
                startActivity(intent)



            }
            R.id.menu_bill -> {
                val intent= Intent(this,Bill::class.java)
                startActivity(intent)



            }
            R.id.menu_sign_out -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, Login::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }


            R.id.menu_profile -> {

                val intent= Intent(this,Profile::class.java)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(nav)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
