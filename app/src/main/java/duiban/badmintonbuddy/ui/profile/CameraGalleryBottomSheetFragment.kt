package duiban.badmintonbuddy.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import duiban.badmintonbuddy.R
import duiban.badmintonbuddy.databinding.FragmentCameraGalleryBottomSheetBinding


class CameraGalleryBottomSheetFragment : BottomSheetDialogFragment() {

    private var cameraGalleryBottomSheetBinding : FragmentCameraGalleryBottomSheetBinding?= null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_camera_gallery_bottom_sheet, container, false)
        val binding = FragmentCameraGalleryBottomSheetBinding.bind(view)
        cameraGalleryBottomSheetBinding = binding

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraGalleryBottomSheetBinding = null
    }

}