package edu.manipal.donatelifemit.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.firebase.ui.database.ObservableSnapshotArray
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import dagger.hilt.android.AndroidEntryPoint
import edu.manipal.donatelifemit.ApplicationState
import edu.manipal.donatelifemit.MainViewModel
import edu.manipal.donatelifemit.R
import edu.manipal.donatelifemit.adaptor.UserAlertAdaptor
import edu.manipal.donatelifemit.pojo.Alert
import edu.manipal.donatelifemit.pojo.IUserAlertListener
import edu.manipal.donatelifemit.ui.dialogs.CovidDialogBuilder
import kotlinx.android.synthetic.main.fragment_current_alerts.*
import kotlinx.android.synthetic.main.fragment_current_alerts.view.*


@AndroidEntryPoint
class CurrentAlertsFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private var adaptor:UserAlertAdaptor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_current_alerts, container, false)
        view.receive_alert_btn.setOnClickListener {
            viewModel.setState(ApplicationState.RECEIVE_ALERTS)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAlertList().observe( viewLifecycleOwner, Observer {
            var query: Query = viewModel.alertDatabase
            val options: FirebaseRecyclerOptions.Builder<Alert> = FirebaseRecyclerOptions.Builder<Alert>().setQuery(query, Alert::class.java)
            adaptor = UserAlertAdaptor(requireContext(), listOf(), options.build(), object : IUserAlertListener {
                override fun registerAvailable(alert: Alert) {
                    viewModel.userDetailsLiveData.value?.phoneNumber?.let{
                        if(!alert.donatorList.contains(it))
                            alert.donatorList.add(it)
                        viewModel.alertDatabase.child(alert.alertID).setValue(alert)
                    }

                }

            })
            alertRecycler.adapter = adaptor
            adaptor?.startListening()
            viewModel.receiveAlertData.observe( viewLifecycleOwner, Observer {
                adaptor?.setContent(it)
            })
        })

        covid_alert_btn.setOnClickListener {
            CovidDialogBuilder().setParentFragmentManager(parentFragmentManager).build()
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            CurrentAlertsFragment().apply {
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
    override fun onDestroy() {
        super.onDestroy()
        adaptor?.stopListening()
    }
}