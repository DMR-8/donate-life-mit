package edu.manipal.donatelifemit.adaptor

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import edu.manipal.donatelifemit.R
import edu.manipal.donatelifemit.pojo.Alert
import kotlinx.android.synthetic.main.cell_received_alert.view.*


class UserAlertAdaptor(private val context: Context, private val options: FirebaseRecyclerOptions<Alert> ) : FirebaseRecyclerAdapter<Alert, UserAlertAdaptor.ViewHolder>(options) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.cell_received_alert, parent, false)
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

            itemView.availableButton.setOnClickListener {

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
    }
}
