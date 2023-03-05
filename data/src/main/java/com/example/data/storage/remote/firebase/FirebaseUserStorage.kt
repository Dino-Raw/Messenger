package com.example.data.storage.remote.firebase

import com.example.data.model.toUser
import com.example.data.storage.remote.UserStorage
import com.example.domain.model.Response
import com.example.domain.model.CurrentUser
import com.example.domain.model.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Named

class FirebaseUserStorage @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    @Named("CurrentUserId") val currentUserId: String,
): UserStorage {
    override suspend fun getUser(userId: String): Flow<Response<User>> = callbackFlow {
        trySend(Response.Loading())

        val listener = firebaseFirestore.collection("users").document(userId)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    trySend(Response.Fail(e = Exception(error.message)))
                    close()
                }

                if (value != null && value.exists())
                    value.toObject(CurrentUser::class.java)?.also {  currentUser ->
                        trySend(Response.Success(data = currentUser.toUser()))
                    }
                else
                    trySend(Response.Fail(e = Exception("No such user")))
          }

        awaitClose {
            listener.remove()
            this.cancel()
        }
    }

    override suspend fun getUserList(): Flow<Response<ArrayList<User>>> = callbackFlow {
        trySend(Response.Loading())

        val listener = firebaseFirestore.collection("users")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    trySend(Response.Fail(e = Exception(error.message)))
                    close()
                }

                val userList = arrayListOf<User>()

                value?.documents?.forEach { snapshot ->
                    snapshot?.toObject(CurrentUser::class.java)?.also {  currentUser ->
                        if (currentUser.id != currentUserId) userList.add(currentUser.toUser())
                    }
                }

                if (userList.isNotEmpty())
                    trySend(Response.Success(data = userList))
            }

        awaitClose {
            listener.remove()
            this.cancel()
        }
    }

    override suspend fun getUserListById(userIdList: ArrayList<String>): Flow<Response<ArrayList<User>>> = callbackFlow {
        trySend(Response.Loading())

        val listener = firebaseFirestore.collection("users")
            //.whereArrayContains("id", userIdList)
            .whereIn("id", userIdList)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    trySend(Response.Fail(e = Exception(error.message)))
                    close()
                }

                val userList = arrayListOf<User>()

                value?.documents?.forEach { snapshot ->
                    snapshot?.toObject(CurrentUser::class.java)?.also {  currentUser ->
                        userList.add(currentUser.toUser())
                    }
                }

                if (userList.isNotEmpty())
                    trySend(Response.Success(data = userList))
            }

        awaitClose {
            listener.remove()
            this.cancel()
        }
    }
}