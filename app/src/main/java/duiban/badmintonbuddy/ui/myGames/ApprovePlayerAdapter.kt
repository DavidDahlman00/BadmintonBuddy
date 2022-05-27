package duiban.badmintonbuddy.ui.myGames

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.type.Color
import duiban.badmintonbuddy.R
import duiban.badmintonbuddy.models.Game
import duiban.badmintonbuddy.models.UserObject

class ApprovePlayerAdapter(game: Game) : RecyclerView.Adapter<ApprovePlayerAdapter.ViewHolder>() {

    val game = game

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ApprovePlayerAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.approve_list_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ApprovePlayerAdapter.ViewHolder, position: Int) {
        holder.itemName.text = game.intrest[position].getValue("name")
        holder.id = game.intrest[position].getValue("id")
        holder.name = game.intrest[position].getValue("name")

        val user = HashMap<String, String>()
        with(user){
            put("id", holder.id)
            put("name", holder.name)
        }
        holder.itemDenieBtn.setOnClickListener {

            Log.d("4444","clicked $position and denie")
            removePlayerFromMatch(user)
        }
        holder.itemAcceptBtn.setOnClickListener {

            Log.d("4444","clicked $position and accept")
            addPlayerToMatch(user)
        }
    }

    override fun getItemCount(): Int {
        return game.intrest.size
    }

    private fun listOfIntrested() {

    }

    private fun addPlayerToMatch(user : HashMap<String, String>) {
        game.players.add(user)
        game.players.distinct()
        game.intrest.removeAll { it.getValue("id") == user.getValue("id") }
    }

    private fun removePlayerFromMatch(user : HashMap<String, String>) {
        game.intrest.add(user)
        game.intrest.distinct()
        game.players.removeAll { it.getValue("id") == user.getValue("id") }
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var itemName : TextView = itemView.findViewById(R.id.approve_list_item_name)
        var itemDenieBtn : Button = itemView.findViewById(R.id.approve_list_item_denie_btn)
        var itemAcceptBtn : Button = itemView.findViewById(R.id.approve_list_item_accept_btn)
        lateinit var id : String
        lateinit var name : String
    }
}