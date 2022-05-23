package duiban.badmintonbuddy.ui.myGames

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import duiban.badmintonbuddy.R
import duiban.badmintonbuddy.databinding.FragmentCreateGameDialogBinding
import duiban.badmintonbuddy.models.Game
import duiban.badmintonbuddy.models.UserObject
import java.util.*


class CreateGameDialogFragment :  DialogFragment(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private var createGameDialogBinding : FragmentCreateGameDialogBinding ?= null
    private val db = Firebase.firestore
    private var day = 0
    private var month = 0
    private var year = 0
    private var hour = 0
    private var minute = 0

    private var savedDay = 0
    private var savedMonth = 0
    private var savedYear = 0
    private var savedHour = 0
    private var savedMinute = 0

    private var numberOfPlayers = 2


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_game_dialog, container, false)
        val binding = FragmentCreateGameDialogBinding.bind(view)
        createGameDialogBinding = binding

        cancel()
        createGame()
        pickDate()
        selectNumberOfplayers()
        return view
    }

    private fun selectNumberOfplayers() {
        createGameDialogBinding?.radioButton2player?.setOnClickListener {
            numberOfPlayers = 2
        }
        createGameDialogBinding?.radioButton4players?.setOnClickListener {
            numberOfPlayers = 4
        }
        Log.d("1111", "number of players $numberOfPlayers")
    }

    private fun cancel() {
        createGameDialogBinding?.cancelBtn?.setOnClickListener {
            dismiss()
        }
    }

    private fun createGame(){
        createGameDialogBinding?.createGameBtn?.setOnClickListener {
            val playerList = mutableListOf<String>()
            val where = createGameDialogBinding?.editTextTextPlace?.text.toString()
            playerList.add(UserObject.thisUser.id)

            Log.d("QQQ", "did send game???")
            val gameRef = db.collection("game").document()
            val newGame = Game(
                id = gameRef.id,
                where = where,
                year = savedYear,
                month = savedMonth,
                day = savedDay,
                hour = savedHour,
                min = savedMinute,
                numPlayers = numberOfPlayers,
                players = playerList
            )
            gameRef.set(newGame)
            dismiss()
        }
    }

    private fun getDateTimeCalander() {
        val cal : Calendar = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        hour = cal.get(Calendar.HOUR)
        minute = cal.get(Calendar.MINUTE)
    }


    private fun pickDate() {
        createGameDialogBinding?.btnTimePicker?.setOnClickListener {
            getDateTimeCalander()
            DatePickerDialog(this.requireContext(),this , year, month, day).show()
        }
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month + 1
        savedYear = year

        getDateTimeCalander()

        TimePickerDialog(this.requireContext(),this , hour, minute, true).show()

    }

    @SuppressLint("SetTextI18n")
    override fun onTimeSet(p0: TimePicker?, hour: Int, min: Int) {
        savedHour = hour
        savedMinute = min
        createGameDialogBinding?.selectedtime?.text = "$savedYear - $savedMonth -$savedDay , $savedHour : $savedMinute "

    }


    override fun onDestroy() {
        super.onDestroy()
        createGameDialogBinding = null
    }
}