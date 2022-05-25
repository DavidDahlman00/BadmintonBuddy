package duiban.badmintonbuddy.ui.myGames

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import duiban.badmintonbuddy.R
import duiban.badmintonbuddy.models.Game

class ApprovePlayerAdapter(game : Game) : RecyclerView.Adapter<ApprovePlayerAdapter.ViewHolder>() {

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
        holder.itemCancelBtn.setOnClickListener {
            Log.d("4444","clicked $position and cancel")
        }
        holder.itemDenieBtn.setOnClickListener {
            Log.d("4444","clicked $position and denie")
        }
        holder.itemAcceptBtn.setOnClickListener {
            Log.d("4444","clicked $position and accept")
        }
    }

    override fun getItemCount(): Int {
        return game.intrest.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var itemName : TextView = itemView.findViewById(R.id.approve_list_item_name)
        var itemCancelBtn : Button = itemView.findViewById(R.id.approve_list_item_cancel_btn)
        var itemDenieBtn : Button = itemView.findViewById(R.id.approve_list_item_denie_btn)
        var itemAcceptBtn : Button = itemView.findViewById(R.id.approve_list_item_accept_btn)
    }
}