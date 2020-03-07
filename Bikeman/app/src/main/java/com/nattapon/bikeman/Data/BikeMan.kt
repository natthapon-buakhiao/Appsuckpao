package com.nattapon.bikeman.Data

class BikeMan (val address:String,val birthday:String,val email:String,val emails:String,
               val fname:String,val lname:String,val password:String,val personid:String,
val phone:String,val profileUrl:String,val bikeid:String) {
    constructor() : this("","","","","",
        "","","","","","") {}
}