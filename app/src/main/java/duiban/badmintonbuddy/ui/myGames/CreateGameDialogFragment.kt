package duiban.badmintonbuddy.ui.myGames

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import duiban.badmintonbuddy.R
import duiban.badmintonbuddy.databinding.FragmentCreateGameDialogBinding
import duiban.badmintonbuddy.models.Game
import duiban.badmintonbuddy.models.UserObject
import duiban.badmintonbuddy.models.ValidationResult
import java.util.*
import kotlin.collections.HashMap


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

    private var numberOfPlayers: Int? = null


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
        selectNumberPlayers()
        return view
    }

    private fun selectNumberPlayers() {
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
    private fun validNewGame(game: Game): ValidationResult{
        if (game.where.isBlank())
            return ValidationResult(false, "Please add location")
        if (game.hasTimePast())
            return ValidationResult(false, "The selected time has already past")
        return ValidationResult(true, "none")
    }

    private fun createGame(){

            createGameDialogBinding?.createGameBtn?.setOnClickListener {
                val playerList = mutableListOf<HashMap<String, String>>()
                val thisPlayerMap = HashMap<String, String>()
                with(thisPlayerMap){
                    put("id", UserObject.thisUser.id)
                    put("name", UserObject.thisUser.name)
                }
                playerList.add(thisPlayerMap)

                val where = createGameDialogBinding?.editTextTextPlace?.text.toString()
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
                    numPlayers = numberOfPlayers ?: 2,
                    players = playerList
                )

                val validation = validNewGame(newGame)

                if (validation.successful){
                    gameRef.set(newGame)
                    dismiss()
                }else{
                    createGameDialogBinding?.createGameErrorText?.text = validation.errorMessage
                }
            }


    }

    private fun getDateTimeCalendar() {
        val cal : Calendar = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        hour = cal.get(Calendar.HOUR)
        minute = cal.get(Calendar.MINUTE)
    }


    private fun pickDate() {
        createGameDialogBinding?.btnTimePicker?.setOnClickListener {
            getDateTimeCalendar()
            DatePickerDialog(this.requireContext(),this , year, month, day).show()
        }
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month + 1
        savedYear = year

        getDateTimeCalendar()

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