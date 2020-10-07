package edu.manipal.donatelifemit.pojo

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class Utility {
    companion object {
        fun getVisualDate(nextDay: Long): String {
            return SimpleDateFormat("dd MMMM, yyyy").format( Date(nextDay));


        }
    }
}