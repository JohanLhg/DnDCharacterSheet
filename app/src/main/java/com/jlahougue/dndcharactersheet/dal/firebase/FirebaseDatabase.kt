package com.jlahougue.dndcharactersheet.dal.firebase

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.jlahougue.dndcharactersheet.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FirebaseDatabase {
    companion object {
        private var INSTANCE: FirebaseDatabase? = null

        private const val TAG_USERS = "users"
        private const val TAG_CHARACTERS = "characters"

        fun getInstance(): FirebaseDatabase {
            if (INSTANCE == null) INSTANCE = FirebaseDatabase()
            return INSTANCE!!
        }
    }

    private val firestore: FirebaseFirestore
    private val auth: FirebaseAuth
    val storage: FirebaseStorage
    var id = ""
    val uid
        get() = auth.uid.toString()
    val userReference: DocumentReference
        get() = firestore.collection(TAG_USERS).document(uid)

    init {
        setID()
        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()
    }

    private fun setID() {
        id = ""
        FirebaseInstallations.getInstance().id
            .addOnSuccessListener { id = it }
    }

    fun characterReference(characterID: Long): DocumentReference {
        return userReference.collection(TAG_CHARACTERS).document(characterID.toString())
    }

    fun updateCharacterSheet(characterID: Long, values: Map<String, Any>) {
        characterReference(characterID).update(values)
    }

    //region Image Functions
    fun uploadImage(imageReference: StorageReference, uri: Uri) {
        //addTasksToQueue(UPLOAD_IMAGE)
        imageReference.putFile(uri)
            .addOnSuccessListener {
                //finishTask(UPLOAD_IMAGE)
            }
            .addOnFailureListener {
                //finishTask(UPLOAD_IMAGE)
            }
    }

    fun deleteImage(characterID: Long) {
        //addTasksToQueue(UPLOAD_IMAGE)
        val storageRef: StorageReference = storage.reference
        val imageRef: StorageReference = storageRef.child("Images/Characters/$uid/$characterID.png")
        imageRef.delete()
    }

    fun loadImage(path: String, context: Context, view: ImageView) {
        CoroutineScope(Dispatchers.IO).launch {
            storage.reference.child(path).downloadUrl.addOnSuccessListener {
                Glide.with(context)
                    .load(it.toString())
                    .thumbnail(Glide.with(context).load(R.drawable.loading))
                    .centerInside()
                    .into(view)
            }.addOnFailureListener {
                Glide.with(context)
                    .load(R.drawable.profile)
                    .centerInside()
                    .into(view)
            }
        }
    }
    //endregion
}