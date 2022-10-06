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
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import duiban.badmintonbuddy.R
import duiban.badmintonbuddy.databinding.FragmentRegisterBinding
import duiban.badmintonbuddy.models.User
import duiban.badmintonbuddy.models.UserObject
import duiban.badmintonbuddy.ui.main.MainActivity

@AndroidEntryPoint
class RegisterFragment() : Fragment() {

    private val viewModel: LoginViewModel by activityViewModels()
    private var registerBinding : FragmentRegisterBinding?= null

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

            viewModel.registerUser(  this.requireContext(),
                userName.text.trim{it <= ' '}.toString(),
                email.text.trim { it <= ' ' }.toString(),
                password.text.trim { it <= ' ' }.toString()
            )
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        registerBinding = null
    }
}