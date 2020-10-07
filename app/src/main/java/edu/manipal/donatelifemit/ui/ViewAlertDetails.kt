package edu.manipal.donatelifemit.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import edu.manipal.donatelifemit.ApplicationState
import edu.manipal.donatelifemit.MainViewModel
import edu.manipal.donatelifemit.R
import kotlinx.android.synthetic.main.fragment_view_alert_details.*


@AndroidEntryPoint
class ViewAlertDetails : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_alert_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.selectedAlert?.let{
            unitText.text = it.units.toString()
            bloodGroupText.text = it.bloodType
            centreNameText.text = it.centreName
            donationTypeText.text = it.donationType

            val arrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                it.donatorList
            )

            registeredUserListView.adapter = arrayAdapter
        }
        back_button.setOnClickListener {
            viewModel.setState(ApplicationState.ADMIN_VIEW_ALERTS)
        }
        edit_button.setOnClickListener {
            viewModel.setState(ApplicationState.ADMIN_EDIT_ALERTS)
        }
    }
    companion object {
        @JvmStatic
        fun newInstance() =
            ViewAlertDetails().apply {
                arguments = Bundle().apply {
                }
            }
    }
}