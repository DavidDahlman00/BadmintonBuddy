package duiban.badmintonbuddy.ui.login

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import duiban.badmintonbuddy.R
import duiban.badmintonbuddy.databinding.FragmentRegisterBinding
import duiban.badmintonbuddy.ui.main.MainActivity


class RegisterFragment() : Fragment() {

    private var registerBinding : FragmentRegisterBinding?= null
    private lateinit var auth: FirebaseAuth
   // private val auth = FirebaseAuth.getInstance()
   private val db = Firebase.firestore
    private lateinit var email: EditText
    private lateinit var userName: EditText
    private lateinit var password: EditText
    private var showPassword = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        val binding = FragmentRegisterBinding.bind(view)
        registerBinding = binding
        auth = Firebase.auth

        email = binding.registertextEmail
        password = binding.registereditTextTextPassword
        userName = binding.registertextUsername
        password.requestFocus()
        val currentUser = auth.currentUser

        Log.d("??",auth.currentUser.toString())
        binding.imageeye.setOnClickListener {
            if (showPassword){
                binding.imageeye.setImageResource(R.drawable.ic_eye)
                password.transformationMethod = PasswordTransformationMethod.getInstance()
            }else{
                binding.imageeye.setImageResource(R.drawable.ic_visibility_off)
                password.transformationMethod = HideReturnsTransformationMethod.getInstance()

            }
            showPassword = !showPassword
        }
        binding.registerButton.setOnClickListener {
            registerUser()
        //gotoMainscreen(this.requireContext())
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        registerBinding = null
    }
    private fun gotoMainscreen(context: Context) {
        val bundle = ActivityOptions.makeCustomAnimation(context, android.R.anim.slide_in_left, android.R.anim.slide_out_right).toBundle()
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent, bundle)
    }

    private fun registerUser(){
        val username = userName.text.trim{it <= ' '}.toString()
        val email = email.text.trim{it <= ' '}.toString()
        val password = password.text.trim{it <= ' '}.toString()
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
                }

                db.collection("users")
                    //.document(userID)
                    //.set(newUser)
                    .add(newUser)
                    .addOnSuccessListener {
                        Log.d("222", "DocumentSnapshot added with ID:")
                        gotoMainscreen(this.requireContext())
                    }.addOnFailureListener { e ->
                        Log.w("222", "Error adding document", e)
                    }
            }else {
                Log.w("222", "Error auth")
            }
        }
    }
}