package duiban.badmintonbuddy.ui.findGames

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import duiban.badmintonbuddy.models.Game
import duiban.badmintonbuddy.models.UserObject
import javax.inject.Inject


class FindGameViewModel @Inject constructor(

) : ViewModel() {
    private var db = FirebaseFirestore.getInstance()
    fun loadGamesList() {
        db.collection("game").addSnapshotListener { value, _ ->
            if (value != null) {
                Log.d("message data length", value.size().toString())
                UserObject.gamesList.clear()
                for (document in value) {
                    Log.d("message data", document.toString())
                    val newItem = document.toObject(Game::class.java)
                    UserObject.gamesList.add(newItem)
                }
            }
        }
    }
}