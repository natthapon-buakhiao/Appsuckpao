package com.nattapon.bikeman.Adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.nattapon.bikeman.Data.BikeMan
import com.nattapon.bikeman.Data.Order
import com.nattapon.bikeman.R

class ProfileAdapter(val mCtx: Context, val layoutResId:Int, val ProfileList:List<BikeMan>)
    : ArrayAdapter<BikeMan>(mCtx,layoutResId,ProfileList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId, null)
        val showFirstName = view.findViewById<TextView>(R.id.show_fname)
        val showLastName = view.findViewById<TextView>(R.id.show_lname)
        val showPersonId = view.findViewById<TextView>(R.id.showPersonId)
        val showBirthday = view.findViewById<TextView>(R.id.showBirthday)
        val showTel = view.findViewById<TextView>(R.id.showTel)
        val showAss = view.findViewById<TextView>(R.id.showAss)

        val firstName = view.findViewById<TextView>(R.id.fname)
        val lastName = view.findViewById<TextView>(R.id.lname)
        val personId = view.findViewById<TextView>(R.id.personId)
        val birthday = view.findViewById<TextView>(R.id.birthday)
        val tel = view.findViewById<TextView>(R.id.tel)
        val address = view.findViewById<TextView>(R.id.ass)

        val btnEdit = view.findViewById<Button>(R.id.btnEditProfile)

        val fname: String = "ชื่อ:"
        val lname: String = "นามสกุล:"
        val pId: String = "เลขบัตรประชาชน:"
        val birthx: String = "วันเกิด:"
        val telx: String = "เบอร์:"
        val ass: String = "ที่อยู่:"

        val profile = ProfileList[position]
        showFirstName.text = profile.fname
        showLastName.text = profile.lname
        showPersonId.text = profile.personid
        showBirthday.text = profile.birthday
        showTel.text = profile.phone
        showAss.text = profile.address

        firstName.text = fname
        lastName.text = lname
        personId.text = pId
        birthday.text = birthx
        tel.text = telx
        address.text = ass

        btnEdit.setOnClickListener {
            editProfile(profile)
        }

        return view
    }

    private fun editProfile(profile: BikeMan) {
        val builder = AlertDialog.Builder(mCtx)
        builder.setTitle("Update")
        val inflater = LayoutInflater.from(mCtx)
        val view = inflater.inflate(R.layout.activity_update_profile, null)

        val etFirstName = view.findViewById<EditText>(R.id.etFirstName)
        val etLastName = view.findViewById<EditText>(R.id.etLastName)
        val etPersonId = view.findViewById<EditText>(R.id.etPersonId)
        val etBirthday = view.findViewById<EditText>(R.id.etBirthday)
        val etTell = view.findViewById<EditText>(R.id.etTell)
        val etAddress = view.findViewById<EditText>(R.id.etAddress)

        etFirstName.setText(profile.fname)
        etLastName.setText(profile.lname)
        etPersonId.setText(profile.personid)
        etBirthday.setText(profile.birthday)
        etTell.setText(profile.phone)
        etAddress.setText(profile.address)

        builder.setView(view)

        builder.setPositiveButton("Update", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                val newFirstName = etFirstName.text.toString().trim()
                val newLastName = etLastName.text.toString().trim()
                val newPersonId = etPersonId.text.toString().trim()
                val newBirthday = etBirthday.text.toString().trim()
                val newTel = etTell.text.toString().trim()
                val newAddress = etAddress.text.toString().trim()

                if (newFirstName.isEmpty()) {
                    etFirstName.error = "Enter status"
                    etFirstName.requestFocus()
                    return
                }
                if (newLastName.isEmpty()) {
                    etLastName.error = "Enter status"
                    etLastName.requestFocus()
                    return
                }
                if (newBirthday.isEmpty()) {
                    etBirthday.error = "Enter status"
                    etBirthday.requestFocus()
                    return
                }

                if (newPersonId.isEmpty()) {
                    etPersonId.error = "Enter status"
                    etPersonId.requestFocus()
                    return
                }
                if (newTel.isEmpty()) {
                    etTell.error = "Enter status"
                    etTell.requestFocus()
                    return
                }
                if (newAddress.isEmpty()) {
                    etAddress.error = "Enter status"
                    etAddress.requestFocus()
                    return
                }
                val uid = FirebaseAuth.getInstance().uid ?: ""
                val ref = FirebaseDatabase.getInstance().getReference("registerbiker/$uid")

                val newProfile = BikeMan(newAddress,newBirthday,profile.email,profile.emails,
                newFirstName,newLastName,profile.password,newPersonId,newTel,profile.profileUrl,uid)

                ref.setValue(newProfile)



            }
        })
        builder.setNegativeButton("Cancel",object : DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {

            }

        })
        val alert = builder.create()
        alert.show()

    }
}