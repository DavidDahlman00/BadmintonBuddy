package duiban.badmintonbuddy.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import duiban.badmintonbuddy.R
import duiban.badmintonbuddy.databinding.ActivityLoginBinding
import duiban.badmintonbuddy.ui.start.TestFire
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    //private val testFire = testFire
    @Inject lateinit var testFire: TestFire
    private val loginFragment = LoginFragment()
    private val registerFragment = RegisterFragment()
    private lateinit var loginBinding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        val view = loginBinding.root
        setContentView(view)

       Log.d("AAA", testFire.doBajs())

        loginBinding.loginBottomNav.setOnItemSelectedListener { item ->

            when(item.itemId){
                R.id.login_link -> switchFragment(loginFragment)
                R.id.register_link -> switchFragment(registerFragment)
            }
            true
        }
    }

    private fun switchFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.loginFragmentContainerView, fragment)
        transaction.commit()
    }
}