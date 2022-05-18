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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import duiban.badmintonbuddy.R
import duiban.badmintonbuddy.databinding.FragmentRegisterBinding
import duiban.badmintonbuddy.models.User
import duiban.badmintonbuddy.ui.main.MainActivity


class RegisterFragment() : Fragment() {

    private var registerBinding : FragmentRegisterBinding?= null
    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore
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

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val userID = task.result?.user?.uid.toString()
                val newUser =
                    User(id = userID, name = username, email = email, password = password)


                db.collection("Users").document(userID).set(newUser)
                    .addOnCompleteListener {
                        Log.d("!!!", "$username successfully registerd")


                    }.addOnFailureListener {
                        Log.d("!!!", "failed to registerd")
                    }

                gotoMainscreen(this.requireContext())

            } else {
                Log.d("!!!", "failed to register")
            }
        }
    }
}