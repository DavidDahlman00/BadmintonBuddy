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
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import duiban.badmintonbuddy.R
import duiban.badmintonbuddy.databinding.FragmentLoginBinding
import duiban.badmintonbuddy.models.User
import duiban.badmintonbuddy.models.UserObject
import duiban.badmintonbuddy.ui.main.MainActivity

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel : LoginViewModel by activityViewModels()
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

        viewModel.errorStatus.observe(viewLifecycleOwner) { result ->
            if (!result.successful) {
                binding.loginError.text = result.errorMessage
            }
        }
        binding.loginError.text = viewModel.errorStatus.value?.errorMessage

        binding.loginButton.setOnClickListener {
            viewModel.login(
                this.requireContext(),
                email.text.toString(),
                password.text.toString()
            )
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        loginFragmentLoginBinding = null
    }
}