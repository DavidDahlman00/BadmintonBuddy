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
import android.widget.EditText
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import duiban.badmintonbuddy.R
import duiban.badmintonbuddy.databinding.FragmentProfileBinding
import duiban.badmintonbuddy.models.User
import duiban.badmintonbuddy.models.UserObject
import duiban.badmintonbuddy.ui.profile.CameraGalleryBottomSheetFragment
import java.io.ByteArrayOutputStream
import java.util.*
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {

    companion object {
        private const val CAMERA_PERMISSION_CODE = 1
        private const val CAMERA_REQUEST_CODE = 2
        private const val IMAGE_REQUEST_CODE = 3
    }

    private var profileBinding: FragmentProfileBinding?= null
    private var takenImage: Bitmap? = null
    private val db = Firebase.firestore
    lateinit var uuid: String
    private lateinit var name: EditText
    private lateinit var email: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val binding = FragmentProfileBinding.bind(view)
        profileBinding = binding
        val cameraGalleryBottomSheetFragment = CameraGalleryBottomSheetFragment(this)

        name = profileBinding?.profileEditName!!
        email = profileBinding?.profileEditEmail!!


        if(UserObject.thisUser.profileImage == ""){
            Log.d("tag", "load ???")
            profileBinding?.profileImage?.setImageResource(R.drawable.ic_camera)
            Log.d("tag", "load standart")
        }else{
            Log.d("tag", "load else")
           Picasso.get().load(UserObject.thisUser.profileImage).into(profileBinding?.profileImage)
            Log.d("tag", "load picasso")
        }

        profileBinding?.profileImage?.setOnClickListener {
            fragmentManager?.let { it1 -> cameraGalleryBottomSheetFragment.show(it1,"camera_gallery_bottom_sheet") }
        }

        profileBinding?.updateProfileButton?.setOnClickListener {
            updateUserToDatabase()
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


    private fun updateUserToDatabase(){
        uuid = UUID.randomUUID().toString()
        val baos = ByteArrayOutputStream()
        val storageRef = FirebaseStorage.getInstance()
            .reference.child(UserObject.thisUser.id).child("ProfileImage/${UserObject.thisUser.id}")

        if (takenImage == null) return

        takenImage!!.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        storageRef.delete().addOnSuccessListener {

        val uploadTask = storageRef.putBytes(data)
            //.child("images/" + UserObject.thisUser.id).putBytes(data)
        uploadTask.addOnFailureListener {
            Log.d("!!!", "on failure  $it")
        }.addOnSuccessListener { taskSnapshot ->
            val url = taskSnapshot.metadata?.reference?.downloadUrl


            url?.addOnSuccessListener {
                val link = it
                Log.d("url", "$it")
                val imageLink = link.toString()
                Log.d("Photo Link", "$imageLink")

                val newUser = User(
                    id = UserObject.thisUser.id,
                    name = name.text.toString(),
                    email = email.text.toString(),
                    password = UserObject.thisUser.password,
                    profileImage = imageLink
                )

                db.collection("users").document(UserObject.thisUser.id).set(newUser)

                Log.d("USER UPDATE", "to the database, good")
            }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        profileBinding = null
    }
}