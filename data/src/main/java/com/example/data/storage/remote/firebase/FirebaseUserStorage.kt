package com.example.data.storage.remote.firebase

import com.example.data.model.toUser
import com.example.data.storage.remote.UserStorage
import com.example.domain.model.Response
import com.example.domain.model.CurrentUser
import com.example.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Named

class FirebaseUserStorage @Inject constructor(
    @Named("CurrentUserId") val currentUserId: String,
    private val firebaseDatabase: FirebaseDatabase,
): UserStorage {
    override suspend fun getUser(userId: String): Flow<Response<User>> = callbackFlow {
        trySend(Response.Loading())

        firebaseDatabase.getReference("/Users/$userId").get()
            .addOnSuccessListener { result ->
                val currentUser = result.getValue(CurrentUser::class.java)

                if (currentUser != null) {
                    trySend(Response.Success(data = currentUser.toUser()))
                } else {
                    trySend(Response.Fail(e = Exception("No such user")))
                }

            }.addOnFailureListener { e ->
                trySend(Response.Fail(e = e))
            }

        awaitClose { this.cancel() }
    }

    override suspend fun getUserList(): Flow<Response<ArrayList<User>>> = callbackFlow {
        trySend(Response.Loading())

        firebaseDatabase.getReference("/Users/").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val userArray: ArrayList<User> = ArrayList()

                snapshot.children.forEach { userSnapshot ->
                    val currentUser: CurrentUser = userSnapshot.getValue(CurrentUser::class.java) as CurrentUser

                    if (currentUser.id != currentUserId)
                        userArray.add(currentUser.toUser())
                }

                trySend(Response.Success(data = userArray))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(Response.Fail(e = error.toException()))
            }

        })

        awaitClose { this.cancel() }
    }
}