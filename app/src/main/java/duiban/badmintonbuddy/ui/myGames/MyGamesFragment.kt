package duiban.badmintonbuddy.myGames

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
import duiban.badmintonbuddy.databinding.FragmentMyGamesBinding
import duiban.badmintonbuddy.models.Game
import duiban.badmintonbuddy.models.UserObject
import duiban.badmintonbuddy.ui.myGames.CreateGameDialogFragment
import duiban.badmintonbuddy.ui.myGames.MyGamesAdapter

class MyGamesFragment : Fragment() {

    private var myGamesBinding : FragmentMyGamesBinding?= null
    private var db = FirebaseFirestore.getInstance()
    private lateinit var recyclerView: RecyclerView
    private var adapter: RecyclerView.Adapter<MyGamesAdapter.ViewHolder>? = null

            override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_games, container, false)
        val binding = FragmentMyGamesBinding.bind(view)
        myGamesBinding = binding
                loadGamesList()
                recyclerView = myGamesBinding!!.myGameRecycler
                recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
                adapter = MyGamesAdapter(this)
                recyclerView.adapter = adapter
                recyclerView.adapter!!.notifyDataSetChanged()

                myGamesBinding?.newgamebtn?.setOnClickListener {
            val createGameDialogFragment = CreateGameDialogFragment()
            createGameDialogFragment.show(parentFragmentManager, "createGameDialogFragment")
        }
        return view
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadGamesList() {
        db.collection("game").addSnapshotListener { value, error ->
            if (value != null) {
                Log.d("message data length", value.size().toString())
                UserObject.gamesList.clear()
                for (document in value!!) {
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
        myGamesBinding = null
    }
}