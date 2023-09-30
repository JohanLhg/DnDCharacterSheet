package com.jlahougue.dndcharactersheet.dal.firebase.dao

import com.google.firebase.firestore.DocumentSnapshot
import com.jlahougue.dndcharactersheet.dal.firebase.FirebaseDatabase
import com.jlahougue.dndcharactersheet.dal.firebase.documentLayouts.CharacterSheet
import kotlin.concurrent.thread

class CharacterSheetDao {
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    fun insert(characterSheet: CharacterSheet) {
        firebaseDatabase.userReference.collection(TAG_CHARACTERS)
            .document(characterSheet.id.toString())
            .set(characterSheet)
    }

    fun loadCharacterSheets(
        saveToLocal: (Long, CharacterSheet) -> Unit,
        callback: (List<Long>) -> Unit
    ) {
        val collectionRef = firebaseDatabase.userReference.collection(TAG_CHARACTERS)
        collectionRef.get()
            .addOnCompleteListener { task ->
                thread {
                    var characterIDs = listOf<Long>()
                    if (task.isSuccessful) {
                        val documents: List<DocumentSnapshot> = task.result!!.documents

                        for (document in documents) {
                            val characterSheet = document.toObject(CharacterSheet::class.java)
                            if (characterSheet != null)
                                saveToLocal(characterSheet.id, characterSheet)
                        }

                        characterIDs = documents.map { it.id.toLong() }
                    }
                    callback(characterIDs)
                }
            }
    }

    companion object {
        private const val TAG_CHARACTERS = "characters"
    }
}