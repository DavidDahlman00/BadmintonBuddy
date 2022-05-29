package duiban.badmintonbuddy.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import duiban.badmintonbuddy.R
import duiban.badmintonbuddy.databinding.FragmentProfileBinding
import duiban.badmintonbuddy.ui.profile.CameraGalleryBottomSheetFragment


class ProfileFragment : Fragment() {

  private var profileBinding: FragmentProfileBinding?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val binding = FragmentProfileBinding.bind(view)
        profileBinding = binding
        val cameraGalleryBottomSheetFragment = CameraGalleryBottomSheetFragment()

        profileBinding?.profileImage?.setOnClickListener {
            fragmentManager?.let { it1 -> cameraGalleryBottomSheetFragment.show(it1,"camera_gallery_bottom_sheet") }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        profileBinding = null
    }
}