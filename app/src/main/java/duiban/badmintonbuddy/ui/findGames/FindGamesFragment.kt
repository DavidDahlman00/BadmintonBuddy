package duiban.badmintonbuddy.ui.findGames

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import duiban.badmintonbuddy.R
import duiban.badmintonbuddy.databinding.FragmentFindGamesBinding


class FindGamesFragment : Fragment() {

    private var findGamesBinding: FragmentFindGamesBinding?=null
   // private var db = FirebaseFirestore.getInstance()
    private lateinit var recyclerView: RecyclerView
    private var adapter: RecyclerView.Adapter<FindGamesAdapter.ViewHolder>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_find_games, container, false)
        val binding = FragmentFindGamesBinding.bind(view)
        findGamesBinding = binding
        recyclerView = findGamesBinding!!.findgamerecycler
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
        adapter = FindGamesAdapter(this)
        recyclerView.adapter = adapter
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        findGamesBinding = null
    }
}