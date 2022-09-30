package duiban.badmintonbuddy.models

import java.time.LocalDate
import java.time.LocalTime
import java.util.*
import kotlin.collections.HashMap

class Game( var id: String = "",
            var where: String = "",
            var year: Int = 0,
            var month: Int = 0,
            var day: Int = 0,
            var hour: Int = 0,
            var min: Int = 0,
            var numPlayers: Int = 2,
            var players: MutableList<HashMap<String, String>> = mutableListOf(),
            var intrest: MutableList<HashMap<String, String>> = mutableListOf()
        )
    {

        fun timeString(): String {
            return if(min > 9){
                "${year}:${month}:${day} - ${hour}:${min}"
            }else{
                "${year}:${month}:${day} - ${hour}:0${min}"
            }

        }

        fun hasTimePast(): Boolean {
        val time_now = Date(System.currentTimeMillis())         // not good fix later =(
        val local = LocalDate.now()
            val localTime = LocalTime.now()
            if (time_now.year + 1900 > year){
                return true
            } else if (time_now.year + 1900 < year){
                return false
            } else if(time_now.month + 1 > month ){
                return true
            } else if(time_now.month + 1 < month ){
                return false
            }else if(local.dayOfMonth > day){
                return true
            } else if(local.dayOfMonth < day){
                return false
            }else if(localTime.hour > hour){
                return true
            } else if(localTime.hour < hour){
                return false
            }else return time_now.minutes > min
        }
}