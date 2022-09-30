package duiban.badmintonbuddy.ui.searchUsers

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import duiban.badmintonbuddy.R
import duiban.badmintonbuddy.databinding.FragmentSearchUsersBinding
import duiban.badmintonbuddy.models.Game
import duiban.badmintonbuddy.models.User
import duiban.badmintonbuddy.models.UserObject


class SearchUsersFragment : Fragment() {

    private var searchUsersBinding : FragmentSearchUsersBinding? = null
    private val db = Firebase.firestore
    private lateinit var recyclerView: RecyclerView
    private var adapter: RecyclerView.Adapter<SearchUserAdapter.ViewHolder>? = null

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search_users, container, false)
        val binding = FragmentSearchUsersBinding.bind(view)
        searchUsersBinding = binding
        recyclerView = searchUsersBinding!!.searchUserRecycler
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
        adapter = SearchUserAdapter()
        recyclerView.adapter = adapter
        recyclerView.adapter!!.notifyDataSetChanged()
        loadUsers()

        searchUsersBinding!!.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("!!!", "text submit")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("!!!", newText as String)
                if (newText!=""){
                    UserObject.userSearchList.clear()
                    val search = newText?.lowercase()
                    for (user in UserObject.userList){
                        if(search?.let { user.name.lowercase().contains(it) }!!){
                           UserObject.userSearchList.add(user)
                        }
                    }
                }
                recyclerView.adapter!!.notifyDataSetChanged()
                return true
            }
        })
        return view
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadUsers(){
        db.collection("users").addSnapshotListener { value, _ ->
            if (value != null) {
                UserObject.userList.clear()
                for (document in value) {
                    Log.d("message data", document.toString())
                    val newItem = document.toObject(User::class.java)
                    if (newItem.id != UserObject.thisUser.id){
                        UserObject.userList.add(newItem)
                        UserObject.userSearchList.add(newItem)
                    }
                }
                recyclerView.adapter!!.notifyDataSetChanged()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        searchUsersBinding = null
    }

}