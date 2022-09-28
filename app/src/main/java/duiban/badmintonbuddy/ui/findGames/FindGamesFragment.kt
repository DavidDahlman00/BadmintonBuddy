package duiban.badmintonbuddy.ui.findGames

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import duiban.badmintonbuddy.R
import duiban.badmintonbuddy.databinding.FragmentFindGamesBinding

class FindGamesFragment: Fragment() {

    private val viewModel: FindGameViewModel by viewModels()
    private var findGamesBinding: FragmentFindGamesBinding?=null
   // private var db = FirebaseFirestore.getInstance()
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
        viewModel.loadGamesList()

        recyclerView = findGamesBinding!!.findgamerecycler
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
        adapter = FindGamesAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.adapter!!.notifyDataSetChanged()
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        findGamesBinding = null
    }
}