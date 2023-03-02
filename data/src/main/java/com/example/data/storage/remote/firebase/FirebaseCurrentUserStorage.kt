package com.example.data.storage.remote.firebase

import com.example.data.storage.remote.CurrentUserStorage
import com.example.domain.model.*
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Named

class FirebaseCurrentUserStorage @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore,
    @Named("CurrentUserId") val currentUserId: String,
): CurrentUserStorage {
    override suspend fun insert(currentUser: CurrentUser): Flow<Response<Boolean>> = callbackFlow {
        trySend(Response.Loading())

        firebaseFirestore.collection("users").document(firebaseAuth.currentUser?.uid.toString()).set(
            currentUser.apply {
                id = firebaseAuth.currentUser?.uid.toString()
                createAt = Timestamp.now().toString()
            }
        ).addOnSuccessListener {
            trySend(Response.Success(data = true))
        }.addOnFailureListener { e ->
            trySend(Response.Fail(e = e ))
        }

        awaitClose { cancel() }
    }

    override suspend fun delete(): Flow<Response<Boolean>> {
        TODO("Not yet implemented")
    }

    override suspend fun update(currentUser: CurrentUser): Flow<Response<Boolean>> {
        TODO("Not yet implemented")
    }

    override suspend fun getUser(): Flow<Response<CurrentUser>> = callbackFlow {
        trySend(Response.Loading())

        val listener = firebaseFirestore.collection("users").document(currentUserId)
            .addSnapshotListener { value, error ->

                if (error != null) {
                    trySend(Response.Fail(e = Exception(error.message)))
                    close()
                }

                if (value != null && value.exists())
                    value.toObject(CurrentUser::class.java)?.also { user ->
                        trySend(Response.Success(data = user))
                    }
                else
                    trySend(Response.Fail(e = Exception("No such user")))
            }

        awaitClose {
            listener.remove()
            cancel()
        }
    }

//    suspend fun getChatList(): Flow<Response<String>> = callbackFlow {
//        trySend(Response.Loading())
//
//        var chatsList: ArrayList<Chat> = arrayListOf()
//        var chatIdList: ArrayList<String>? = arrayListOf()
//
//        firebaseDatabase.getReference("/Users/$currentUserId").get()
//            .addOnSuccessListener { userSnapshot ->
//                chatIdList = userSnapshot.getValue(CurrentUser::class.java)?.chats
//
//                if (chatIdList.isNullOrEmpty())
//                    trySend(Response.Fail(e = Exception("No such chats")))
//
//            }.addOnFailureListener { e ->
//                trySend(Response.Fail(e = e))
//            }
//
//        chatIdList?.forEach { chatId ->
//            var message: Message? = null
//            var user: User? = null
//
//            firebaseDatabase.getReference("/Messages/$chatId").get()
//                .addOnSuccessListener { messageSnapshot ->
//                    message = messageSnapshot.children.last().getValue(Message::class.java)
//
//                    if (message == null)
//                        trySend(Response.Fail(e = Exception("No such chat $chatId")))
//
//                }.addOnFailureListener { e ->
//                    trySend(Response.Fail(e = e))
//                }
//
//            firebaseDatabase.getReference("Users/${chatId.replace(currentUserId, "")}").get()
//                .addOnSuccessListener { userSnapshot ->
//                    user = userSnapshot.getValue(CurrentUser::class.java)?.toUser()
//
//                    if (user == null)
//                        trySend(Response.Fail(e = Exception("No such user in chat $chatId")))
//
//                }.addOnFailureListener { e ->
//                    trySend(Response.Fail(e = e))
//                }
//
//            if (message != null && user != null)
//                chatsList.add (
//                    Chat (
//                        id = chatId,
//                        recentMessage = message,
//                        toUser = user,
//                    )
//                )
//        }
//
//        if (chatsList.isEmpty())
//            trySend(Response.Success(data = chatsList))
//        else
//            trySend(Response.Fail(e = Exception("No such chats")))
//
//        awaitClose { this.cancel() }
//    }
}