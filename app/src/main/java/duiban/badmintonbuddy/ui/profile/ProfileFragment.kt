package duiban.badmintonbuddy.profile

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import duiban.badmintonbuddy.R
import duiban.badmintonbuddy.databinding.FragmentProfileBinding
import duiban.badmintonbuddy.ui.profile.CameraGalleryBottomSheetFragment


class ProfileFragment : Fragment() {

    companion object {
        private const val CAMERA_PERMISSION_CODE = 1
        private const val CAMERA_REQUEST_CODE = 2
        private const val IMAGE_REQUEST_CODE = 3
    }

    private var profileBinding: FragmentProfileBinding?= null
    private var takenImage: Bitmap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val binding = FragmentProfileBinding.bind(view)
        profileBinding = binding
        val cameraGalleryBottomSheetFragment = CameraGalleryBottomSheetFragment(this)

        profileBinding?.profileImage?.setOnClickListener {
            fragmentManager?.let { it1 -> cameraGalleryBottomSheetFragment.show(it1,"camera_gallery_bottom_sheet") }
        }

        return view
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == CAMERA_PERMISSION_CODE){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, CAMERA_REQUEST_CODE)
            }else{
                Log.d("camera", "permission denied")

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if((requestCode == CAMERA_REQUEST_CODE)&&(resultCode == Activity.RESULT_OK)){
            takenImage = data!!.extras!!.get("data") as Bitmap
            profileBinding?.profileImage?.setImageBitmap(takenImage)
        }
        if((requestCode == IMAGE_REQUEST_CODE)&&(resultCode == Activity.RESULT_OK)){
            takenImage = data!!.extras!!.get("data") as Bitmap
            profileBinding?.profileImage?.setImageBitmap(takenImage)
        }
    }

    fun takePicture() {
        Log.d("take picture", "profile is runing takePicture()")
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED){
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, CAMERA_REQUEST_CODE)
        }else{
              ActivityCompat.requestPermissions(
                  requireActivity(), arrayOf(android.Manifest.permission.CAMERA),
                CAMERA_PERMISSION_CODE)
        }
    }

    fun pickImageGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        profileBinding = null
    }
}