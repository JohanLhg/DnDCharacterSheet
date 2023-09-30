package com.jlahougue.dndcharactersheet.dal.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.storage.FirebaseStorage

class FirebaseDatabase {
    companion object {
        private var INSTANCE: FirebaseDatabase? = null

        private const val TAG_USERS = "users"

        fun getInstance(): FirebaseDatabase {
            if (INSTANCE == null) INSTANCE = FirebaseDatabase()
            return INSTANCE!!
        }
    }

    private val firestore: FirebaseFirestore
    private val auth: FirebaseAuth
    private val storage: FirebaseStorage
    private var id = ""
    private val uid
        get() = auth.uid.toString()
    private val userReference: DocumentReference
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
}