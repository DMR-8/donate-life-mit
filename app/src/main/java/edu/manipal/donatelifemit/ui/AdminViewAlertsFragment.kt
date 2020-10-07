package edu.manipal.donatelifemit.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.Query
import dagger.hilt.android.AndroidEntryPoint
import edu.manipal.donatelifemit.ApplicationState
import edu.manipal.donatelifemit.MainViewModel
import edu.manipal.donatelifemit.R
import edu.manipal.donatelifemit.adaptor.AdminAlertAdaptor
import edu.manipal.donatelifemit.pojo.Alert
import edu.manipal.donatelifemit.pojo.IViewEditAlertListener
import kotlinx.android.synthetic.main.fragment_admin_view_alerts.*

@AndroidEntryPoint
class AdminViewAlertsFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()

    private var adaptor: AdminAlertAdaptor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_admin_view_alerts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val options: FirebaseRecyclerOptions.Builder<Alert> = FirebaseRecyclerOptions.Builder<Alert>()
        var query: Query = viewModel.alertDatabase

        options.setQuery(query, Alert::class.java)

        adaptor = AdminAlertAdaptor(requireContext(), options.build(), object : IViewEditAlertListener {
            override fun onEditClick(alert: Alert) {
                viewModel.selectedAlert = alert
                viewModel.setState(ApplicationState.ADMIN_EDIT_ALERTS)
            }

            override fun onDeleteClick(alert: Alert) {
                viewModel.alertDatabase.child(alert.alertID).removeValue()
            }

            override fun onViewClick(alert: Alert) {
                viewModel.selectedAlert = alert
                viewModel.setState(ApplicationState.VIEW_ALERT_DETAILS)
            }

        })
        alertRecycler.adapter = adaptor
        adaptor?.startListening()
    }
    companion object {
        @JvmStatic
        fun newInstance() =
            AdminViewAlertsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
    override fun onStart() {
        super.onStart()
        adaptor?.startListening()
    }

    override fun onStop() {
        super.onStop()
        adaptor?.stopListening()
    }
}