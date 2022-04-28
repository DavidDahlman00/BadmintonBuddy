package duiban.badmintonbuddy.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import duiban.badmintonbuddy.R
import duiban.badmintonbuddy.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment() {

    private var registerBinding : FragmentRegisterBinding?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        val binding = FragmentRegisterBinding.bind(view)
        registerBinding = binding


        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        registerBinding = null
    }
}