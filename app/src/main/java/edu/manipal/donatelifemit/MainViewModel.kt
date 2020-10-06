package edu.manipal.donatelifemit

import androidx.appcompat.app.AppCompatActivity
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import edu.manipal.donatelifemit.pojo.Alert
import edu.manipal.donatelifemit.pojo.User

enum class ApplicationState {
    CURRENT_ALERTS,
    UPCOMING_EVENTS,
    RECEIVE_ALERTS,
    ADMIN_LOG_IN,
    ADMIN_ACCESS,
    ADMIN_VIEW_ALERTS,
    ADMIN_EDIT_ALERTS,
    ADD_ALERT,
    FINISH
}

class MainViewModel @ViewModelInject constructor(): ViewModel() {
    var adminLogin:Boolean = false
    var selectedAlert: Alert? = null
     var userDatabase: DatabaseReference = Firebase.database.reference.child("users")
    var alertDatabase: DatabaseReference = Firebase.database.reference.child("alerts")
    var adminDatabase: DatabaseReference = Firebase.database.reference.child("admin")
    var userDetailsLiveData: MutableLiveData<User> = MutableLiveData()
    var receiveAlertData: MutableLiveData<List<String>> = MutableLiveData()

    private var ApplicationStateLiveData: MutableLiveData<ApplicationState> = MutableLiveData()

    fun setState(state: ApplicationState) {
        ApplicationStateLiveData.value = state
    }
    fun getApplicationStateLiveData(): LiveData<ApplicationState> {
        return ApplicationStateLiveData
    }

    fun readUser(){
        var tempUser:User = User(FirebaseAuth.getInstance().currentUser!!.phoneNumber!!, FirebaseAuth.getInstance().currentUser!!.uid, listOf())

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                dataSnapshot.getValue<List<String>>()?.let{
                    userDetailsLiveData.value?.receiveAlertList = it
                    receiveAlertData.value = it
                } ?: run {
                    userDetailsLiveData.value?.receiveAlertList = listOf()
                }


                // ...
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                // ...
            }
        }
        tempUser.phoneNumber.let { userDatabase.child(it).child("receiveAlertList").addValueEventListener(postListener) }
        userDetailsLiveData.value = tempUser
    }
    fun updateUserDetails() {
        userDetailsLiveData.value?.phoneNumber?.let { userDatabase.child(it).setValue(userDetailsLiveData.value) }
    }

    fun getUserData(): MutableLiveData<User> {
        return userDetailsLiveData
    }
    fun getAlertList(): MutableLiveData<List<String>> {
        return receiveAlertData
    }
}