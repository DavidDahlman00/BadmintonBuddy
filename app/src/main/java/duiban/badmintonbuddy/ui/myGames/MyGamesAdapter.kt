package duiban.badmintonbuddy.ui.myGames

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import duiban.badmintonbuddy.R
import duiban.badmintonbuddy.models.Game
import duiban.badmintonbuddy.models.UserObject

class MyGamesAdapter(fragment : Fragment): RecyclerView.Adapter<MyGamesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyGamesAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_game_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyGamesAdapter.ViewHolder, position: Int) {
        val filterGames = UserObject.gamesList.
        filter { it.players[0].getValue("id") == UserObject.thisUser.id }.
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
        filter { it.players[0].getValue("id") == UserObject.thisUser.id }.
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

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var itemTime: TextView = itemView.findViewById(R.id.myitemtimetext)
        var itemPlace: TextView = itemView.findViewById(R.id.myitemplacetext)
        var itemPlayer1: TextView = itemView.findViewById(R.id.myitemplayer1_btn)
        var itemPlayer2: TextView = itemView.findViewById(R.id.myitemplayer2_btn)
        var itemPlayer3: TextView = itemView.findViewById(R.id.myitemplayer3_btn)
        var itemPlayer4: TextView = itemView.findViewById(R.id.myitemplayer4_btn)
        var itemApprovePlayers: Button = itemView.findViewById(R.id.mygame_watingapprove_btn)

        lateinit var game : Game
    }
}