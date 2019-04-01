package com.chetu.kotlinchat.custom.util

import java.text.SimpleDateFormat
import java.util.*

class DateUtils {

    companion object {

        fun convertTimeToHHMM(time: Long?): String {
            val outputDateFormat = SimpleDateFormat("HH:mm")
            val date = Date(time!!)

            val timeString = outputDateFormat.format(date)
            return timeString
        }
    }
}