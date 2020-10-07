package edu.manipal.donatelifemit.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import edu.manipal.donatelifemit.ApplicationState
import edu.manipal.donatelifemit.MainViewModel
import edu.manipal.donatelifemit.R
import kotlinx.android.synthetic.main.fragment_admin_login.*

@AndroidEntryPoint
class AdminLoginFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginButton.setOnClickListener {
            if( usernameField.text.toString() == "kmcbloodbank" && passwordField.text.toString() == "manipal123456"){
                viewModel.adminLogin = true
                viewModel.setState(ApplicationState.ADMIN_ACCESS)
            } else {
                viewModel.adminLogin = false
                Toast.makeText(context, "Incorrect Credentials, Please Try again with proper credentials", Toast.LENGTH_SHORT).show()
            }

        }
    }
    companion object {

        @JvmStatic
        fun newInstance() =
            AdminLoginFragment().apply {

            }
    }
}