package duiban.badmintonbuddy.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import duiban.badmintonbuddy.databinding.FragmentCameraGalleryBottomSheetBinding
import duiban.badmintonbuddy.profile.ProfileFragment


class CameraGalleryBottomSheetFragment(fragment: ProfileFragment) : BottomSheetDialogFragment() {
    private var thisParentFragment = fragment
    private var cameraGalleryBottomSheetBinding : FragmentCameraGalleryBottomSheetBinding?= null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(duiban.badmintonbuddy.R.layout.fragment_camera_gallery_bottom_sheet, container, false)
        val binding = FragmentCameraGalleryBottomSheetBinding.bind(view)
        cameraGalleryBottomSheetBinding = binding

        cameraGalleryBottomSheetBinding?.imageViewCameraBottomSheet?.setOnClickListener {
            thisParentFragment.takePicture()
            Log.d("bottomSheet", "will take picture")
            dismiss()
        }

        cameraGalleryBottomSheetBinding?.imageViewGalleryBottomSheet?.setOnClickListener {
            thisParentFragment.pickImageGallery()
            Log.d("bottomSheet", "will take picture from gallery")
            dismiss()
        }

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraGalleryBottomSheetBinding = null
    }

}