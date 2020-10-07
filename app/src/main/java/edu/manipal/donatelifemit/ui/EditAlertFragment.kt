package edu.manipal.donatelifemit.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import edu.manipal.donatelifemit.ApplicationState
import edu.manipal.donatelifemit.MainViewModel
import edu.manipal.donatelifemit.R
import edu.manipal.donatelifemit.pojo.Alert
import kotlinx.android.synthetic.main.fragment_add_alert.*
import kotlinx.android.synthetic.main.fragment_edit_alert.*
import kotlinx.android.synthetic.main.fragment_edit_alert.back_button
import kotlinx.android.synthetic.main.fragment_edit_alert.bloodGroupSpinner
import kotlinx.android.synthetic.main.fragment_edit_alert.centreNameText
import kotlinx.android.synthetic.main.fragment_edit_alert.donationTypeSpinner
import kotlinx.android.synthetic.main.fragment_edit_alert.unitText


@AndroidEntryPoint
class EditAlertFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private val bloodTypeList: ArrayList<String> = ArrayList()
    private val donationTypeList: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bloodTypeList.add("A +ve")
        bloodTypeList.add("A -ve")
        bloodTypeList.add("B +ve")
        bloodTypeList.add("B -ve")
        bloodTypeList.add("AB +ve")
        bloodTypeList.add("AB -ve")
        bloodTypeList.add("O +ve")
        bloodTypeList.add("O -ve")
        bloodTypeList.add("Rare Blood Groups")

        donationTypeList.add("Blood")
        donationTypeList.add("Platelet")

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_alert, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        back_button.setOnClickListener {
            viewModel.setState(ApplicationState.ADMIN_ACCESS)
        }

        val bloodAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item, bloodTypeList
        )
        val donationAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item, donationTypeList
        )
        donationTypeSpinner.adapter = donationAdapter
        bloodGroupSpinner.adapter = bloodAdapter


        viewModel.selectedAlert?.let{alert ->
            donationTypeSpinner.setSelection(donationTypeList.indexOf(alert.donationType))
            bloodGroupSpinner.setSelection(bloodTypeList.indexOf(alert.bloodType))
            centreNameText.setText(alert.centreName)
            unitText.setText(alert.units.toString())
            update_button.setOnClickListener {
                var newAlert = Alert(
                    donationTypeSpinner.selectedItem.toString(),
                    bloodGroupSpinner.selectedItem.toString(),
                    unitText.text.toString().toInt(),
                    System.currentTimeMillis(),
                    centreNameText.text.toString(),
                    alert.alertID
                    )
                viewModel.alertDatabase.child(alert.alertID).setValue(newAlert)
                viewModel.setState(ApplicationState.ADMIN_ACCESS)
            }
        }


    }

    companion object {

        @JvmStatic
        fun newInstance() =
            EditAlertFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}