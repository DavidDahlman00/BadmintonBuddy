package duiban.badmintonbuddy.ui.findGames

import android.app.AlertDialog
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import duiban.badmintonbuddy.R
import duiban.badmintonbuddy.models.Game
import duiban.badmintonbuddy.models.User
import duiban.badmintonbuddy.models.UserObject

class FindGamesAdapter(fragment : Fragment): RecyclerView.Adapter<FindGamesAdapter.ViewHolder>() {

    val fragment = fragment

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FindGamesAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.searchgameitem, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: FindGamesAdapter.ViewHolder, position: Int) {
        val filterGames = UserObject.gamesList.
                filter { it.players[0].getValue("id") != UserObject.thisUser.id }.
                filter { it.run { !it.hasTimePast() } }
        val game = filterGames[position]
        holder.game = game
        holder.itemPlace.text = game.where
        holder.itemTime.text = "${game.year}:${game.month}:${game.day} - ${game.hour}:${game.min}"
        holder.itemPlayer1.text = findPlayerOrEmpty(game, 0)
        holder.itemPlayer2.text = findPlayerOrEmpty(game, 1)
        if (game.numPlayers == 4){
            holder.itemPlayer3.text = findPlayerOrEmpty(game, 2)
            holder.itemPlayer4.text = findPlayerOrEmpty(game, 3)
        }else{
            holder.itemPlayer3.visibility = View.GONE
            holder.itemPlayer4.visibility = View.GONE
        }

    }

    override fun getItemCount(): Int {
        return  UserObject.gamesList.
                filter { it.players[0].getValue("id") != UserObject.thisUser.id }.
                filter { it.run { !it.hasTimePast() } }
                .size
    }

    private fun findPlayerOrEmpty(game: Game,pos : Int) : String{
        if (game.players.size > pos){
            return game.players[pos].getValue("name")
        }else{
            return "open"
        }
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var itemTime: TextView = itemView.findViewById(R.id.searchitemtimetext)
        var itemPlace: TextView = itemView.findViewById(R.id.searchitemplacetext)
        var itemPlayer1: TextView = itemView.findViewById(R.id.searchitemplayer1_btn)
        var itemPlayer2: TextView = itemView.findViewById(R.id.searchitemplayer2_btn)
        var itemPlayer3: TextView = itemView.findViewById(R.id.searchitemplayer3_btn)
        var itemPlayer4: TextView = itemView.findViewById(R.id.searchitemplayer4_btn)

        lateinit var game : Game
        init {
                itemPlayer2.setOnClickListener {
                    if(hasAlreadyAsked()){
                        Log.d("toaast", "toast!?")
                        showHasAlreadyAsked()
                    }else{
                        asktoJoinGame(itemPlayer2.text.toString())
                    }
                }
                itemPlayer3.setOnClickListener {
                    if(hasAlreadyAsked()){
                        showHasAlreadyAsked()
                    }else{
                        asktoJoinGame(itemPlayer3.text.toString())
                    }
                }
                itemPlayer4.setOnClickListener {
                    if(hasAlreadyAsked()){
                        showHasAlreadyAsked()
                    }else{
                        asktoJoinGame(itemPlayer4.text.toString())
                    }
                }

            }

        private fun asktoJoinGame(buttonText : String){
            if ((buttonText == "open") && (itemPlayer1.text != UserObject.thisUser.id)){
                Log.d("yes", "is clicked")
                val askToJoinDialog = AskToJoinGameDialogFragment(game)
                askToJoinDialog.show(fragment.parentFragmentManager, "askToJoinDialog")
            }
        }

        private fun hasAlreadyAsked() : Boolean {
            val thisPlayersMap = HashMap<String, String>()
            with(thisPlayersMap){
                put("id", UserObject.thisUser.id)
                put("name", UserObject.thisUser.name)
            }
            return game.intrest.contains(thisPlayersMap)
        }

        private fun showHasAlreadyAsked(){
            val dialogBuilder = AlertDialog.Builder(fragment.requireContext())

            dialogBuilder.setMessage("")
                .setCancelable(false)
                .setPositiveButton("Ok", DialogInterface.OnClickListener {
                        dialog, id -> dialog.cancel()
                })

            val alert = dialogBuilder.create()
            alert.setTitle("You have already asked to join this match")
            alert.show()
        }
    }
}