package duiban.badmintonbuddy.ui.login

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import duiban.badmintonbuddy.models.User
import duiban.badmintonbuddy.models.UserObject
import duiban.badmintonbuddy.models.ValidationResult
import duiban.badmintonbuddy.ui.main.MainActivity
import kotlinx.coroutines.flow.MutableStateFlow

class LoginViewModel: ViewModel() {

    private  val auth: FirebaseAuth = Firebase.auth
    private val db = Firebase.firestore

    private val _errorStatus = MutableLiveData<ValidationResult>()
    val errorStatus: LiveData<ValidationResult> = _errorStatus

    init {
        _errorStatus.value = ValidationResult(true, "")
    }

    private fun gotoMainScreen(context: Context) {
        val bundle = ActivityOptions.makeCustomAnimation(
            context, android.R.anim.slide_in_left,
            android.R.anim.slide_out_right
        ).toBundle()
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent, bundle)
    }

     fun login(context: Context, email: String, password: String) {
        val email = email.trim { it <= ' ' }
        val password = password.trim { it <= ' ' }

        if (email.isNotBlank() && password.isNotBlank()){
           signIn(context, email, password)
        }else{
            Log.d("666","blank email or password")
            _errorStatus.value = ValidationResult(false, "blank email or password")
        }
    }

    fun registerUser(context: Context,
                             userName: String,
                             email: String,
                             password: String){
        val username = userName.trim{it <= ' '}
        val email = email.trim{it <= ' '}
        val password = password.trim{it <= ' '}
        Log.d("???","test register")
        Log.d("test22", username )
        Log.d("test22", email )
        Log.d("test22", password )
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            Log.d("111", task.toString())
            if (task.isSuccessful) {
                val userID = task.result?.user?.uid.toString()
                Log.d("test22", userID )
                Log.d("test22", username )
                Log.d("test22", email )
                Log.d("test22", password )
                val newUser = hashMapOf<String, Any>()
                with(newUser) {
                    put("id", userID)
                    put("name", username)
                    put("password", password)
                    put("email", email)
                    put("profileImage", "")
                }

                db.collection("users")
                    .document(userID)
                    .set(newUser)
                    .addOnSuccessListener {
                        Log.d("222", "DocumentSnapshot added with ID:")
                        val thisUser = User(userID, username, email, "")
                        UserObject.thisUser = thisUser
                 gotoMainScreen(context)
                    }.addOnFailureListener { e ->
                        Log.w("222", "Error adding document", e)
                    }
            }else {
                Log.w("222", "Error auth")
                _errorStatus.value = ValidationResult(false, "failed to login")
            }
        }
    }

    private fun signIn(context: Context, email: String, password: String){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val userID = task.result?.user?.uid.toString()
                Log.d("test22", userID)
                db.collection("users").document(userID).addSnapshotListener { document, e ->
                    if (e != null) {
                        Log.d("login", "data failed to load")
                    }
                    if (document != null && document.exists()) {
                        val userdata = document.toObject(User::class.java)
                        Log.d("login", userdata.toString())
                        if (userdata != null) {
                            UserObject.thisUser = userdata
                            Log.d("111", UserObject.thisUser.email)
                            Log.d("111profileImage", UserObject.thisUser.profileImage)
                        }
                    }
                    Log.d("TAG", "signInWithEmail:success")
                }
                gotoMainScreen(context)
            }else{
                Log.d("666","failed to login")
                _errorStatus.value = ValidationResult(false, "Failed to login")
            }
        }
    }
}