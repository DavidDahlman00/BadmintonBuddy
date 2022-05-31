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
import duiban.badmintonbuddy.databinding.FragmentLoginBinding
import duiban.badmintonbuddy.models.User
import duiban.badmintonbuddy.models.UserObject
import duiban.badmintonbuddy.ui.main.MainActivity

class LoginFragment : Fragment() {

    private var loginFragmentLoginBinding: FragmentLoginBinding? = null
    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore
    private lateinit var email: EditText
    private lateinit var password: EditText
    private var showPassword = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        val binding = FragmentLoginBinding.bind(view)
        loginFragmentLoginBinding = binding
        auth = Firebase.auth
        email = binding.logintextUseremail
        password = binding.editTextTextPassword
        password.requestFocus()
        binding.imagelogineye.setOnClickListener {
            if (showPassword) {
                binding.imagelogineye.setImageResource(R.drawable.ic_eye)
                password.transformationMethod = PasswordTransformationMethod.getInstance()
            } else {
                binding.imagelogineye.setImageResource(R.drawable.ic_visibility_off)
                password.transformationMethod = HideReturnsTransformationMethod.getInstance()

            }
            showPassword = !showPassword
        }

        binding.loginButton.setOnClickListener {
            login()
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        loginFragmentLoginBinding = null
    }

    private fun gotoMainScreen(context: Context) {
        val bundle = ActivityOptions.makeCustomAnimation(
            context, android.R.anim.slide_in_left,
            android.R.anim.slide_out_right
        ).toBundle()
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent, bundle)
    }

    private fun login() {
        val email = email.text.trim { it <= ' ' }.toString()
        val password = password.text.trim { it <= ' ' }.toString()

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
                        }
                    }
                    Log.d("TAG", "signInWithEmail:success")
                }
                gotoMainScreen(this.requireContext())
            }
        }
    }
}