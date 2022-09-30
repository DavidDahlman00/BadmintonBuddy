package duiban.badmintonbuddy.ui.login

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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

    private val viewModel : LoginViewModel by viewModels()
    private var loginFragmentLoginBinding: FragmentLoginBinding? = null
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
            viewModel.login(
                this.requireContext(),
                email.text.trim { it <= ' ' }.toString(),
                password.text.trim { it <= ' ' }.toString()
            )
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        loginFragmentLoginBinding = null
    }
}