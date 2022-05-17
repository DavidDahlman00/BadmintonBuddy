package duiban.badmintonbuddy.ui.login

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import duiban.badmintonbuddy.R
import duiban.badmintonbuddy.databinding.FragmentLoginBinding
import duiban.badmintonbuddy.ui.main.MainActivity

class LoginFragment : Fragment() {

    private var loginFragmentLoginBinding:FragmentLoginBinding? = null
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
            if (showPassword){
                binding.imagelogineye.setImageResource(R.drawable.ic_eye)
                password.transformationMethod = PasswordTransformationMethod.getInstance()
            }else{
                binding.imagelogineye.setImageResource(R.drawable.ic_visibility_off)
                password.transformationMethod = HideReturnsTransformationMethod.getInstance()

            }
            showPassword = !showPassword
        }

        binding.loginButton.setOnClickListener {
            gotoMainscreen(this.requireContext())
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        loginFragmentLoginBinding = null
    }

    private fun gotoMainscreen(context: Context) {
        val bundle = ActivityOptions.makeCustomAnimation(context, android.R.anim.slide_in_left, android.R.anim.slide_out_right).toBundle()
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent, bundle)
    }
}