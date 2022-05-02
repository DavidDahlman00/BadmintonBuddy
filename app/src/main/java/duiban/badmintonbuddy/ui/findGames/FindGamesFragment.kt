package duiban.badmintonbuddy.ui.findGames

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import duiban.badmintonbuddy.R
import duiban.badmintonbuddy.databinding.FragmentFindGamesBinding


class FindGamesFragment : Fragment() {

    private var findGamesBinding: FragmentFindGamesBinding?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_find_games, container, false)
        val binding = FragmentFindGamesBinding.bind(view)
        findGamesBinding = binding
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        findGamesBinding = null
    }
}