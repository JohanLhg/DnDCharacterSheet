package com.jlahougue.dndcharactersheet.dal.repositories

import com.google.firebase.auth.FirebaseAuth

class AuthRepository {
    private var auth = FirebaseAuth.getInstance()

    companion object {
        private var instance: AuthRepository? = null

        fun getInstance(): AuthRepository {
            if (instance == null) {
                instance = AuthRepository()
            }
            return instance!!
        }
    }

    fun isLoggedIn() = auth.currentUser != null

    fun register(email: String, password: String, callback: (Boolean) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                callback(task.isSuccessful)
            }
    }

    fun login(email: String, password: String, callback: (Boolean) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                callback(task.isSuccessful)
            }
    }

    fun changeEmail(email: String, callback: (Boolean) -> Unit) {
        auth.currentUser?.updateEmail(email)
            ?.addOnCompleteListener { task ->
                callback(task.isSuccessful)
            }
    }

    fun changePassword(password: String, callback: (Boolean) -> Unit) {
        auth.currentUser?.updatePassword(password)
            ?.addOnCompleteListener { task ->
                callback(task.isSuccessful)
            }
    }

    fun signOut() = auth.signOut()
}