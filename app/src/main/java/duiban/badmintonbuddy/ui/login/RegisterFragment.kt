package duiban.badmintonbuddy.ui.login

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import duiban.badmintonbuddy.R
import duiban.badmintonbuddy.databinding.FragmentRegisterBinding
import duiban.badmintonbuddy.main.MainActivity


class RegisterFragment() : Fragment() {

    private var registerBinding : FragmentRegisterBinding?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        val binding = FragmentRegisterBinding.bind(view)
        registerBinding = binding
        binding.registerButton.setOnClickListener {
            gotoMainscreen(this.requireContext())
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
}