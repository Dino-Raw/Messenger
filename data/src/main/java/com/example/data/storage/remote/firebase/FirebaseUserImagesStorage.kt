package com.example.data.storage.remote.firebase

import android.net.Uri
import com.example.data.storage.remote.UserImagesStorage
import com.example.domain.model.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class FirebaseUserImagesStorage @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseStorage: FirebaseStorage,
): UserImagesStorage {
    override suspend fun insert(imageProfilePath: String): Flow<Response<String>> = callbackFlow {
        trySend(Response.Loading())

        val imageReference =
            firebaseStorage.getReference("/${firebaseAuth.currentUser?.uid}/profile.jpg")

        imageReference.putFile(Uri.parse(imageProfilePath)).addOnCompleteListener {
            imageReference.downloadUrl.addOnSuccessListener { uri ->
                trySend(Response.Success(data = uri.toString()))
            }.addOnFailureListener { e ->
                trySend(Response.Fail(e = e))
            }
        }.addOnFailureListener { e ->
            trySend(Response.Fail(e = e))
        }

        awaitClose { this.cancel() }
    }

    override suspend fun delete(): Flow<Response<Boolean>> = callbackFlow {
        trySend(Response.Loading())

        val imageReference =
            firebaseStorage.getReference("/${firebaseAuth.currentUser?.uid}/profile.jpg")

        imageReference.delete()
            .addOnSuccessListener {
                trySend(Response.Success(data = true))
            }.addOnFailureListener { e ->
                trySend(Response.Fail(e = e))
            }

        awaitClose { this.cancel() }
    }
}