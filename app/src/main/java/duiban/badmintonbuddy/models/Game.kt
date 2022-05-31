package duiban.badmintonbuddy.models

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
            if(min > 9){
                return "${year}:${month}:${day} - ${hour}:${min}"
            }else{
                return "${year}:${month}:${day} - ${hour}:0${min}"
            }

        }

        fun hasTimePast(): Boolean {
        val time_now = Date(System.currentTimeMillis())
        if (time_now.year > year){
            return true
        } else if (time_now.year < year){
            return false
        } else if(time_now.month > month){
            return true
        } else if(time_now.month < month){
            return false
        }else if(time_now.day > day){
            return true
        } else if(time_now.day < day){
            return false
        }else if(time_now.hours > hour){
            return true
        } else if(time_now.hours < hour){
            return false
        }else return time_now.minutes > min
    }
}