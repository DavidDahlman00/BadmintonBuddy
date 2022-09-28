package duiban.badmintonbuddy.ui.profile

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.provider.MediaStore
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import duiban.badmintonbuddy.models.User
import duiban.badmintonbuddy.models.UserObject
import duiban.badmintonbuddy.profile.ProfileFragment
import java.io.ByteArrayOutputStream
import java.util.*
import javax.inject.Inject

class ProfileViewModel @Inject constructor(

): ViewModel() {
    lateinit var uuid: String
    private var takenImage: Bitmap? = null
    private val db = Firebase.firestore

     fun updateUserToDatabase(name : String, email : String){
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
                        name = name,
                        email = email,
                        password = UserObject.thisUser.password,
                        profileImage = imageLink
                    )

                    db.collection("users").document(UserObject.thisUser.id).set(newUser)

                    Log.d("USER UPDATE", "to the database, good")
                }
            }
        }
    }
}