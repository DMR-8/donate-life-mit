package edu.manipal.donatelifemit.adaptor


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.manipal.donatelifemit.R
import edu.manipal.donatelifemit.pojo.IBloodTypeSelectListener
import kotlinx.android.synthetic.main.cell_blood_type_alert.view.*
import java.util.*

class BloodTypeAdaptor(
    val context: Context?,
    var bloodTypeList: ArrayList<String>,
    var userTypeList: List<String>,
    private val listener: IBloodTypeSelectListener
) : RecyclerView.Adapter<BloodTypeAdaptor.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val viewGroup: ViewGroup = inflater.inflate(R.layout.cell_blood_type_alert, parent, false) as ViewGroup
        val viewHolder = ViewHolder(viewGroup)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bloodType: String = this.bloodTypeList[position]
        holder.bind(bloodType)
    }

    override fun getItemCount(): Int {
        return this.bloodTypeList.size
    }

    fun setContent(bloodTypeList: ArrayList<String>, userList: List<String>) {
        this.bloodTypeList = bloodTypeList
        this.userTypeList = userList
        this.notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(bloodType: String) {
            itemView.bloodText.text = bloodType
            if(userTypeList.contains(bloodType)) {
                itemView.checkbox.isChecked = true
                listener.addToList(bloodType)
            }
            itemView.checkbox.setOnCheckedChangeListener { compoundButton, b ->
                if(b)
                    listener.addToList(bloodType)
                else
                    listener.removeFromList(bloodType)
            }
        }
    }
}