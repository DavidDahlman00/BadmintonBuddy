package duiban.badmintonbuddy.ui.main

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import duiban.badmintonbuddy.R
import duiban.badmintonbuddy.databinding.FragmentSignOutDialogBinding
import duiban.badmintonbuddy.ui.login.LoginActivity


class SignOutDialogFragment : DialogFragment() {

    private var signOutDialogBinding: FragmentSignOutDialogBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_out_dialog, container, false)
        val binding = FragmentSignOutDialogBinding.bind(view)

        signOutDialogBinding = binding

        signOutDialogBinding?.signOutButton?.setOnClickListener {
            logOut(this.requireContext())
        }

        signOutDialogBinding?.cancelButton?.setOnClickListener {
            dismiss()
        }
        return view
    }

    override fun onDestroyView(){
        super.onDestroyView()
        signOutDialogBinding = null
    }

    private fun logOut(context: Context) {
        val bundle = ActivityOptions.makeCustomAnimation(context, android.R.anim.slide_in_left, android.R.anim.slide_out_right).toBundle()
        val intent = Intent(context, LoginActivity::class.java)
        context.startActivity(intent, bundle)
    }
}