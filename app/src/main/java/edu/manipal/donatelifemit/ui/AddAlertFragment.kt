package edu.manipal.donatelifemit.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import dagger.hilt.android.AndroidEntryPoint
import edu.manipal.donatelifemit.ApplicationState
import edu.manipal.donatelifemit.MainViewModel
import edu.manipal.donatelifemit.R
import edu.manipal.donatelifemit.pojo.Alert
import edu.manipal.donatelifemit.pojo.User
import kotlinx.android.synthetic.main.fragment_add_alert.*


@AndroidEntryPoint
class AddAlertFragment : Fragment() {
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
        // Inflate the layout for this fragment
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


        return inflater.inflate(R.layout.fragment_add_alert, container, false)
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

        add_button.setOnClickListener {
            if(!donationTypeSpinner.selectedItem.toString().isNullOrEmpty() && !bloodGroupSpinner.selectedItem.toString().isNullOrEmpty()
                && !centreNameText.text.isNullOrEmpty() && !unitText.text.isNullOrEmpty()) {

                var newPostRef =  viewModel.alertDatabase.push()
                var newAlert = Alert(
                    donationTypeSpinner.selectedItem.toString(),
                    bloodGroupSpinner.selectedItem.toString(),
                    unitText.text.toString().toInt(),
                    System.currentTimeMillis(),
                    centreNameText.text.toString(),
                    alertID = newPostRef.key.toString()
                )
                newPostRef.setValue(newAlert)
                viewModel.setState(ApplicationState.ADMIN_ACCESS)
            } else {
                Toast.makeText(requireContext(), "Please fill in all the Values", Toast.LENGTH_SHORT).show()
            }
        }
    }
    companion object {

        @JvmStatic
        fun newInstance() =
            AddAlertFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}