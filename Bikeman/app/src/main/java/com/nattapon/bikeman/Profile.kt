package com.nattapon.bikeman

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.nattapon.bikeman.Adapter.ProfileAdapter
import com.nattapon.bikeman.Data.BikeMan


class Profile : AppCompatActivity() {

    private var database = FirebaseDatabase.getInstance()
    private var myRef = database.reference
    private var mAuth: FirebaseAuth? = null
    lateinit var OrderList:MutableList<BikeMan>
    lateinit var ref: DatabaseReference
    lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        OrderList= mutableListOf()

       val uid = FirebaseAuth.getInstance().uid ?: ""

        ref = FirebaseDatabase.getInstance().getReference("registerbiker")
        listView = findViewById(R.id.profileList)

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {

                if(p0.exists()){
                    val children =p0.children
                    children.forEach{
                        println(it.toString())
                        OrderList.add(it.getValue(BikeMan::class.java)!!)

                    }

                    val adapter = ProfileAdapter(
                        this@Profile,
                        R.layout.profile,OrderList
                    )

                    listView.adapter=adapter


                }


            }

        })
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
