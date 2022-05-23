package duiban.badmintonbuddy.models

import java.util.*

class Game( var id: String = "",
            var creater: String = "",
            var where: String = "",
            var place: String = "",
            var year: Int = 0,
            var month: Int = 0,
            var day: Int = 0,
            var hour: Int = 0,
            var min: Int = 0,
            var numPlayers: Int = 2,
            var players: MutableList<String> = mutableListOf()
        )
    {

    private fun hasTimePast(): Boolean {
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