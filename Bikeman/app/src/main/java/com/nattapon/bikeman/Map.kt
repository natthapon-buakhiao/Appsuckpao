package com.nattapon.bikeman

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth

class Map : AppCompatActivity() {
    lateinit var mapFrangmant : SupportMapFragment
    lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {

        val lat:String = intent.getStringExtra("latitude")
        val lng:String = intent.getStringExtra("longitude")

        val latitude:Double = lat.toDouble()
        val longitude:Double = lng.toDouble()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        mapFrangmant = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFrangmant.getMapAsync(OnMapReadyCallback {

            googleMap = it
            googleMap.isMyLocationEnabled = true

            val location1 = LatLng(latitude,longitude)
            googleMap.addMarker(MarkerOptions().position(location1).title("Order"))
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location1,14f))

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


