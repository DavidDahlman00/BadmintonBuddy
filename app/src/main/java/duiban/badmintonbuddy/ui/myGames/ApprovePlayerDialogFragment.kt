package duiban.badmintonbuddy.ui.myGames

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import duiban.badmintonbuddy.R
import duiban.badmintonbuddy.databinding.FragmentApprovePlayerDialogBinding
import duiban.badmintonbuddy.models.Game



class ApprovePlayerDialogFragment(game : Game) : DialogFragment() {

    private var approvePlayerDialogBinding : FragmentApprovePlayerDialogBinding ?= null
    val game = game
    private val db = Firebase.firestore
    private lateinit var recyclerView: RecyclerView
    private var adapter: RecyclerView.Adapter<ApprovePlayerAdapter.ViewHolder>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_approve_player_dialog, container, false)
        val binding = FragmentApprovePlayerDialogBinding.bind(view)
        approvePlayerDialogBinding = binding
        recyclerView = approvePlayerDialogBinding!!.approveRecycler
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
        adapter = ApprovePlayerAdapter(game)
        recyclerView.adapter = adapter
        recyclerView.adapter!!.notifyDataSetChanged()

        approvePlayerDialogBinding?.approveListCancelBtn?.setOnClickListener {
            dismiss()
        }

        approvePlayerDialogBinding?.approvListUpdateDbBtn?.setOnClickListener {
            db.collection("game").document(game.id).set(game)
            Log.d("gameList", "${game.players.toString()}, and ${game.intrest.toString()}")
            dismiss()
        }

        return view
    }
}