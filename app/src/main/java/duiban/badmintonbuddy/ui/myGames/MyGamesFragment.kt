package duiban.badmintonbuddy.myGames

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import duiban.badmintonbuddy.R
import duiban.badmintonbuddy.databinding.FragmentMyGamesBinding

class MyGamesFragment : Fragment() {

    private var myGamesBinding : FragmentMyGamesBinding?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_games, container, false)
        val binding = FragmentMyGamesBinding.bind(view)
        myGamesBinding = binding
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        myGamesBinding = null
    }
}