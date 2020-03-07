package com.nattapon.bikeman.Data



class NewOrder(val orderid:String,
    val num:String,
    val laundry:String,
    val store:String,
    val status:String,
          val userid:String,
          val detail:String,val price:String,
          val date:String,val storeid:String,
          val latitude:String,val longitude:String) {
        constructor():this("","","","","","","",
            "","","","",""){

        }

}