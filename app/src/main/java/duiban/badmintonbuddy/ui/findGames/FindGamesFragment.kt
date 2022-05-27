package duiban.badmintonbuddy.ui.findGames

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import duiban.badmintonbuddy.R
import duiban.badmintonbuddy.databinding.FragmentFindGamesBinding
import duiban.badmintonbuddy.models.Game
import duiban.badmintonbuddy.models.UserObject


class FindGamesFragment : Fragment() {

    private var findGamesBinding: FragmentFindGamesBinding?=null
    private var db = FirebaseFirestore.getInstance()
    private lateinit var recyclerView: RecyclerView
    private var adapter: RecyclerView.Adapter<FindGamesAdapter.ViewHolder>? = null

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_find_games, container, false)
        val binding = FragmentFindGamesBinding.bind(view)
        findGamesBinding = binding
        loadGamesList()
        recyclerView = findGamesBinding!!.findgamerecycler
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
        adapter = FindGamesAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.adapter!!.notifyDataSetChanged()
        return view
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun loadGamesList() {
        db.collection("game").addSnapshotListener { value, error ->
            if (value != null) {
                Log.d("message data length", value.size().toString())
                UserObject.gamesList.clear()
                for (document in value) {
                    Log.d("message data", document.toString())
                    val newItem = document.toObject(Game::class.java)
                    UserObject.gamesList.add(newItem)
                }
            }
            recyclerView.adapter!!.notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        findGamesBinding = null
    }
}