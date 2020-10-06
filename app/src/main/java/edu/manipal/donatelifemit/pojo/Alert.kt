package edu.manipal.donatelifemit.pojo

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Alert(var donationType: String = "", var bloodType: String = "", var units: Int = 0, var centreName: String = "", var alertID: String ="") {
    fun Alert(){
        donationType = ""
        bloodType = ""
        units = 0
        centreName = ""
        alertID =""
    }
}
