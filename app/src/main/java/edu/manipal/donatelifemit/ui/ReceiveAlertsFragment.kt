package edu.manipal.donatelifemit.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import edu.manipal.donatelifemit.ApplicationState
import edu.manipal.donatelifemit.MainViewModel
import edu.manipal.donatelifemit.R
import edu.manipal.donatelifemit.adaptor.BloodTypeAdaptor
import edu.manipal.donatelifemit.pojo.IBloodTypeSelectListener
import kotlinx.android.synthetic.main.fragment_current_alerts.view.*
import kotlinx.android.synthetic.main.fragment_receive_alerts.*
import kotlinx.android.synthetic.main.fragment_receive_alerts.view.*

@AndroidEntryPoint
class ReceiveAlertsFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private val bloodTypeList: ArrayList<String> = ArrayList()
    private val userList: ArrayList<String> = ArrayList()
    private var adaptor: BloodTypeAdaptor? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_receive_alerts, container, false)
        bloodTypeList.add("A +ve")
        bloodTypeList.add("A -ve")
        bloodTypeList.add("B +ve")
        bloodTypeList.add("B -ve")
        bloodTypeList.add("AB +ve")
        bloodTypeList.add("AB -ve")
        bloodTypeList.add("O +ve")
        bloodTypeList.add("O -ve")
        bloodTypeList.add("Rare Blood Groups")
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backButton.setOnClickListener {
            viewModel.setState(ApplicationState.CURRENT_ALERTS)
        }

        adaptor = BloodTypeAdaptor(context,  bloodTypeList, listOf(), object: IBloodTypeSelectListener{
            override fun addToList(bloodtype: String) {
                if(!userList.contains(bloodtype)){
                    userList.add(bloodtype)
                }
            }

            override fun removeFromList(bloodtype: String) {
                if(userList.contains(bloodtype)){
                    userList.remove(bloodtype)
                }
            }

        })
        bloodRecycler.adapter = adaptor
        viewModel.receiveAlertData.observe( viewLifecycleOwner, Observer {
            adaptor?.setContent(bloodTypeList, it)
        })


        receive_alert_btn.setOnClickListener {
            viewModel.userDetailsLiveData.value!!.receiveAlertList = userList
            viewModel.updateUserDetails()
            viewModel.setState(ApplicationState.CURRENT_ALERTS)
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            ReceiveAlertsFragment().apply {
            }
    }
}