package com.example.data.storage.remote.firebase

import com.example.data.storage.remote.CurrentUserStorage
import com.example.domain.model.Response
import com.example.domain.model.CurrentUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class FirebaseCurrentUserStorage @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase,
): CurrentUserStorage {
    override suspend fun insert(currentUser: CurrentUser): Flow<Response<Boolean>> = callbackFlow {
        trySend(Response.Loading())

        firebaseDatabase.getReference("/Users/${firebaseAuth.currentUser?.uid}")
            .setValue(currentUser.apply { id = firebaseAuth.currentUser?.uid })
            .addOnSuccessListener {
                trySend(Response.Success(data = true))
            }.addOnFailureListener { e ->
                trySend(Response.Fail(e = e ))
            }

        awaitClose { this.cancel() }
    }

    override suspend fun delete(): Flow<Response<Boolean>> {
        TODO("Not yet implemented")
    }

    override suspend fun update(currentUser: CurrentUser): Flow<Response<Boolean>> {
        TODO("Not yet implemented")
    }

    override suspend fun getUser(): Flow<Response<CurrentUser>> {
        TODO("Not yet implemented")
    }
}