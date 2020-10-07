package edu.manipal.donatelifemit.pojo

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Alert(var donationType: String = "", var bloodType: String = "", var units: Int = 0, var postTime:Long = 0,
                 var centreName: String = "", var alertID: String ="", var donatorList: ArrayList<String> = ArrayList()) {
    fun Alert(){
        donationType = ""
        bloodType = ""
        units = 0
        centreName = ""
        alertID =""
        donatorList = ArrayList()
    }
}
