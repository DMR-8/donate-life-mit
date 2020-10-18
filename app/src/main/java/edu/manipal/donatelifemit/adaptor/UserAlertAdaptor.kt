package edu.manipal.donatelifemit.adaptor

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import edu.manipal.donatelifemit.R
import edu.manipal.donatelifemit.pojo.Alert
import edu.manipal.donatelifemit.pojo.IUserAlertListener
import edu.manipal.donatelifemit.pojo.Utility
import kotlinx.android.synthetic.main.cell_received_alert.view.*


class UserAlertAdaptor(private val context: Context,
                       private var bloodTypeList:List<String>,
                       private val options: FirebaseRecyclerOptions<Alert>,
                        private val listener: IUserAlertListener) : FirebaseRecyclerAdapter<Alert, UserAlertAdaptor.ViewHolder>(options) {

    private val showList:ArrayList<Alert> = ArrayList<Alert>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: ViewGroup = LayoutInflater.from(parent.context).inflate(R.layout.cell_received_alert, parent, false) as ViewGroup
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Alert) {
        if(bloodTypeList.contains(model.bloodType))
            holder.bind(model)
        else{
            holder.hide()
            val params = holder.itemView.layoutParams
            params.height = 0
            holder.itemView.layoutParams = params
        }
    }
    fun setContent(receiverList: List<String>) {
        this.bloodTypeList = receiverList
        this.notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(alert: Alert) {
            itemView.centerNameText.text = alert.centreName
            itemView.bloodTypeText.text = alert.bloodType
            itemView.unitText.text = alert.units.toString() + " Units"
            itemView.postTimeText.text = "Posted: " + Utility.getVisualDate(alert.postTime)

            itemView.availableButton.setOnClickListener {
                listener.registerAvailable(alert)
                Toast.makeText(context, "You have registered as a donor for the request, You will be contacted by our team shortly.", Toast.LENGTH_SHORT).show()
            }

            itemView.shareButton.setOnClickListener {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT,
                    alert.centreName+" requires " + alert.units + " units of " + alert.bloodType + " blood. Your assistance would be helpful.")
                intent.putExtra(Intent.EXTRA_SUBJECT, "Share")
                context.startActivity(Intent.createChooser(intent, "Share using"))
            }
        }
        fun hide() {
            itemView.visibility = View.GONE
        }
    }
}
