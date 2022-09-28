package duiban.badmintonbuddy.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import duiban.badmintonbuddy.R
import duiban.badmintonbuddy.databinding.ActivityMainBinding
import duiban.badmintonbuddy.myGames.MyGamesFragment
import duiban.badmintonbuddy.profile.ProfileFragment
import duiban.badmintonbuddy.ui.findGames.FindGamesFragment
import duiban.badmintonbuddy.ui.searchUsers.SearchUsersFragment


class MainActivity : AppCompatActivity() {
    private val findGamesFragment = FindGamesFragment()
    private val myGamesFragment = MyGamesFragment()
    private val profileFragment = ProfileFragment()
    private val searchUserFragment = SearchUsersFragment()
    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)
        switchFragment(findGamesFragment)

        mainBinding.mainBottomNav.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.find_games_link -> switchFragment(findGamesFragment)
                R.id.profile_link -> switchFragment(profileFragment)
                R.id.my_games_link -> switchFragment(myGamesFragment)
                R.id.search_user_link -> switchFragment(searchUserFragment)
            }
            true
        }
        mainBinding.toptoolbar.logout.setOnClickListener {
            val signOutDialog = SignOutDialogFragment(this)
            signOutDialog.show(supportFragmentManager, "SignOutDialogFragment")
            Log.d("222", "logout")
        }
    }

    private fun switchFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.mainFragmentContainerView, fragment)
        transaction.commit()
    }
}