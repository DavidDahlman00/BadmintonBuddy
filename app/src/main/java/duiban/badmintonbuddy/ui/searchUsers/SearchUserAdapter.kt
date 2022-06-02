package duiban.badmintonbuddy.ui.searchUsers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import duiban.badmintonbuddy.R
import duiban.badmintonbuddy.models.UserObject

class SearchUserAdapter : RecyclerView.Adapter<SearchUserAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchUserAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_user_item, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: SearchUserAdapter.ViewHolder, position: Int) {
        holder.itemName.text = UserObject.userSearchList[position].name
        holder.itemEmail.text = UserObject.userSearchList[position].email
        if (UserObject.userSearchList[position].profileImage == ""){
            holder.itemImage.setImageResource(R.drawable.ic_person)
        }else{
            Picasso.
            get().
            load(UserObject.userSearchList[position].profileImage).
            into(holder.itemImage)
        }
    }

    override fun getItemCount(): Int {
        return UserObject.userSearchList.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var itemName: TextView = itemView.findViewById(R.id.search_user_name_text)
        var itemEmail: TextView = itemView.findViewById(R.id.search_user_email_text)
        var itemImage: ImageView = itemView.findViewById(R.id.search_item_image)
    }
}