package edu.manipal.donatelifemit.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import edu.manipal.donatelifemit.ApplicationState
import edu.manipal.donatelifemit.MainViewModel
import edu.manipal.donatelifemit.R
import kotlinx.android.synthetic.main.fragment_admin_view.*

@AndroidEntryPoint
class AdminViewFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signout_button.setOnClickListener {
            viewModel.adminLogin = false
            viewModel.setState(ApplicationState.ADMIN_LOG_IN)
        }
        add_alert.setOnClickListener {
            viewModel.setState(ApplicationState.ADD_ALERT)
        }
        view_edit_button.setOnClickListener {
            viewModel.setState(ApplicationState.ADMIN_VIEW_ALERTS)
        }
        schedule_event.setOnClickListener {
            Toast.makeText(requireContext(), "This feature will be coming soon!!", Toast.LENGTH_SHORT).show()
        }
    }
    companion object {
        @JvmStatic
        fun newInstance() =
            AdminViewFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}