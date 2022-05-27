package duiban.badmintonbuddy.models

object UserObject {
   var thisUser : User = User()
   var gamesList : MutableList<Game> = mutableListOf()
   var userList : MutableList<User> = mutableListOf()
   var userSearchList : MutableList<User> = mutableListOf()
}