package edu.manipal.donatelifemit


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import edu.manipal.donatelifemit.ApplicationState.*
import edu.manipal.donatelifemit.ui.*
import kotlinx.android.synthetic.main.activity_main.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private val providers = arrayListOf(
        AuthUI.IdpConfig.PhoneBuilder().setDefaultCountryIso("IN").build()
    )
    val PREFERENCE_NAME = "donatelife_preferences"
    val IF_VISITED = "if_visited"


    private var FIRST_TIME=1
    private var CHECK_SIGN_IN=2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        nav_view.setOnNavigationItemSelectedListener(object :
            BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.getItemId()) {
                    R.id.navigation_current_alerts -> {
                        viewModel.setState(CURRENT_ALERTS)
                        return true
                    }
                    R.id.navigation_upcoming_events -> {
                        viewModel.setState(UPCOMING_EVENTS)
                        return true
                    }
                    R.id.navigation_admin_view ->{
                        viewModel.setState(ADMIN_LOG_IN)
                        return true
                    }
                }
                return true
            }
        })

        viewModel.getApplicationStateLiveData().observe(this, Observer {
            when(it) {
                ADMIN_LOG_IN -> {
                    if(viewModel.adminLogin)
                    {
                        viewModel.setState(ADMIN_ACCESS)
                    }else{
                        replaceFragment(AdminLoginFragment.newInstance(),R.id.host_fragment)
                    }
                }
                ADMIN_ACCESS -> {
                    if(!viewModel.adminLogin)
                    {
                        viewModel.setState(ADMIN_LOG_IN)
                    }else{
                        replaceFragment(AdminViewFragment.newInstance(),R.id.host_fragment)
                    }
                }
                ADD_ALERT -> {
                    replaceFragment(AddAlertFragment.newInstance(),R.id.host_fragment)
                }
                CURRENT_ALERTS -> {
                    replaceFragment(CurrentAlertsFragment.newInstance(),R.id.host_fragment)
                }
                UPCOMING_EVENTS -> {
                    replaceFragment(UpcomingEventsFragment.newInstance(),R.id.host_fragment)
                }
                RECEIVE_ALERTS -> {
                    replaceFragment(ReceiveAlertsFragment.newInstance(),R.id.host_fragment)
                }
                FINISH -> {
                    finish()
                }
                ADMIN_VIEW_ALERTS -> {
                    replaceFragment(AdminViewAlertsFragment.newInstance(),R.id.host_fragment)
                }
                ADMIN_EDIT_ALERTS -> {
                    replaceFragment(EditAlertFragment.newInstance(),R.id.host_fragment)
                }
                VIEW_ALERT_DETAILS -> {
                    replaceFragment(ViewAlertDetails.newInstance(),R.id.host_fragment)
                }
            }
        })
        val preferences = applicationContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        var ifVisited = preferences.getBoolean(IF_VISITED, false)
        if(!ifVisited) {
            var intent = Intent(this@MainActivity, GuidelinesActivity::class.java)
            startActivityForResult(intent, FIRST_TIME)
        } else if(FirebaseAuth.getInstance().currentUser == null) {
            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build(),
                CHECK_SIGN_IN)
        } else
        {
            viewModel.readUser()
            viewModel.setState(CURRENT_ALERTS)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CHECK_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                viewModel.setState(CURRENT_ALERTS)
                viewModel.readUser()
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
        if (requestCode == FIRST_TIME) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val preferences = applicationContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
                val editor = preferences.edit()
                editor.putBoolean(IF_VISITED, true)
                editor.apply()
                if(FirebaseAuth.getInstance().currentUser == null) {
                    startActivityForResult(
                        AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(),
                        CHECK_SIGN_IN)
                } else {
                    val user = FirebaseAuth.getInstance().currentUser
                    viewModel.setState(CURRENT_ALERTS)
                    viewModel.readUser()
                }
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }

}