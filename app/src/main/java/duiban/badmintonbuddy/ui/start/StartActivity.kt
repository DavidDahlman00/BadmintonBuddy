package duiban.badmintonbuddy.start

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import duiban.badmintonbuddy.R
import duiban.badmintonbuddy.ui.login.LoginActivity

class StartActivity : AppCompatActivity() {
    private val screenShownInMiliSeconds : Long = 1500
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        Handler(Looper.getMainLooper()).postDelayed({
            gotoLoginScreen(this)
        }, screenShownInMiliSeconds)
    }
    private fun gotoLoginScreen(context: Context) {
        val bundle = ActivityOptions.makeCustomAnimation(context, android.R.anim.slide_in_left, android.R.anim.slide_out_right).toBundle()
        val intent = Intent(context, LoginActivity::class.java)
        context.startActivity(intent, bundle)
    }

}