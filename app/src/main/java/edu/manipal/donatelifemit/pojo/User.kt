package edu.manipal.donatelifemit.pojo

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(var phoneNumber: String, var userID: String, var receiveAlertList: List<String>)