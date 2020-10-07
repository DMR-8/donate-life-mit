package edu.manipal.donatelifemit.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import edu.manipal.donatelifemit.R
import edu.manipal.donatelifemit.pojo.Alert
import edu.manipal.donatelifemit.pojo.IViewEditAlertListener
import edu.manipal.donatelifemit.pojo.Utility
import kotlinx.android.synthetic.main.cell_edit_alert.view.*

class AdminAlertAdaptor(private val context: Context,
                        private val options: FirebaseRecyclerOptions<Alert>,
                        private val listener: IViewEditAlertListener) : FirebaseRecyclerAdapter<Alert, AdminAlertAdaptor.ViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.cell_edit_alert, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Alert) {
            holder.bind(model)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(alert: Alert) {
            itemView.centerNameText.text = alert.centreName
            itemView.bloodTypeText.text = alert.bloodType
            itemView.unitText.text = alert.units.toString() + " Units"
            itemView.postTimeText.text = "Posted: " + Utility.getVisualDate(alert.postTime)
            itemView.changeButton.setOnClickListener {
                listener.onEditClick(alert)
            }

            itemView.viewAlertDetails.setOnClickListener{
                listener.onViewClick(alert)
            }
            itemView.clearButton.setOnClickListener {
                listener.onDeleteClick(alert)
            }
        }
    }

}
