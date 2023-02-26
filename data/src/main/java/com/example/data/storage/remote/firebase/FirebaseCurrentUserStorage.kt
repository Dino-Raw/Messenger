package com.example.data.storage.remote.firebase

import com.example.data.storage.remote.CurrentUserStorage
import com.example.domain.model.Response
import com.example.domain.model.CurrentUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Named

class FirebaseCurrentUserStorage @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase,
    @Named("CurrentUserId") val currentUserId: String,
): CurrentUserStorage {
    override suspend fun insert(currentUser: CurrentUser): Flow<Response<Boolean>> = callbackFlow {
        trySend(Response.Loading())

        firebaseDatabase.getReference("/Users/$currentUserId")
            .setValue(currentUser.apply { id = currentUserId })
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

    override suspend fun getUser(): Flow<Response<CurrentUser>> = callbackFlow {
        trySend(Response.Loading())

        firebaseDatabase.getReference("/Users/$currentUserId").get()
            .addOnSuccessListener { userSnapshot ->
                val user = userSnapshot.getValue(CurrentUser::class.java)
                if (user != null)
                    trySend(Response.Success(data = user))
                else
                    trySend(Response.Fail(e = Exception("No such user")))
            }.addOnFailureListener { e ->
                trySend(Response.Fail(e = e))
            }

        awaitClose { this.cancel() }
    }
}