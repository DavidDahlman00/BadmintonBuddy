package duiban.badmintonbuddy.ui.findGames

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import duiban.badmintonbuddy.R
import duiban.badmintonbuddy.databinding.FragmentAskToJoinGameDialogBinding
import duiban.badmintonbuddy.models.Game
import duiban.badmintonbuddy.models.UserObject


class AskToJoinGameDialogFragment(_game : Game) : DialogFragment() {

    private var askToJoinGameDialogBinding : FragmentAskToJoinGameDialogBinding ?= null
    private val db = Firebase.firestore
    private val game = _game

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ask_to_join_game_dialog, container, false)
        val binding = FragmentAskToJoinGameDialogBinding.bind(view)
        askToJoinGameDialogBinding = binding

        askToJoinGameDialogBinding?.cancelBtnAskDialog?.setOnClickListener {
            dismiss()
        }

        askToJoinGameDialogBinding?.confirmBtnAskDialog?.setOnClickListener {
            askToJoinGame()
            dismiss()
        }

        return view
    }

    private fun askToJoinGame(){
        val intrestedList = game.intrest
        val newIntrested = HashMap<String, String>()
        with(newIntrested){
            put("id", UserObject.thisUser.id)
            put("name", UserObject.thisUser.name)
        }
        Log.d("222", "id ${UserObject.thisUser.id}")
        Log.d("222", "name ${UserObject.thisUser.name}")
        intrestedList.add(newIntrested)
        game.intrest = intrestedList
        db.collection("game").document(game.id).set(game)
    }

    override fun onDestroy() {
        super.onDestroy()
        askToJoinGameDialogBinding = null
    }
}