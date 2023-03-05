package com.example.data.storage.remote.firebase

import com.example.data.model.toUser
import com.example.data.storage.remote.ChatStorage
import com.example.domain.model.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Named

class FirebaseChatStorage @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    @Named("CurrentUserId") private val currentUserId: String,
): ChatStorage {
    override suspend fun insertChat(toUserId: String): Flow<Response<Chat>> = callbackFlow {
        trySend(Response.Loading())

        firebaseFirestore.collection("chats").document().also { document ->
            document.set(Chat(
                id = document.id,
                members = arrayListOf(currentUserId, toUserId).also { it.sort() }
            ))
        }.get().addOnSuccessListener {
            trySend(Response.Success(data = it.toObject(Chat::class.java) as Chat))
        }.addOnFailureListener { e ->
            trySend(Response.Fail(e = e))
        }

        awaitClose { this.cancel() }
    }

    override suspend fun getChatById(chatId: String): Flow<Response<Chat>> = callbackFlow {
        trySend(Response.Loading())

        firebaseFirestore.collection("chats").document(chatId)
            .get()
            .addOnSuccessListener { snapshot ->
                if(snapshot != null && snapshot.exists())
                    snapshot.toObject(Chat::class.java)?.also { chat ->
                        trySend(Response.Success(data = chat))
                    }
                else
                    trySend(Response.Fail(e = Exception("No such chat")))
            }.addOnFailureListener {
                    e ->
                trySend(Response.Fail(e = e))
            }

        awaitClose { this.cancel() }
    }

    override suspend fun getChatByMember(memberId: String): Flow<Response<Chat>> = callbackFlow {
        trySend(Response.Loading())

        firebaseFirestore.collection("chats")
            .whereEqualTo("members", listOf(currentUserId, memberId).sorted())
            .get()
            .addOnSuccessListener {  snapshot ->
                if(snapshot != null && !snapshot.isEmpty)
                    snapshot.documents.first().toObject(Chat::class.java)?.also { chat ->
                        trySend(Response.Success(data = chat))
                    }
                else
                    trySend(Response.Fail(e = Exception("No such chat")))
            }.addOnFailureListener { e ->
                trySend(Response.Fail(e = e))
            }

        awaitClose { this.cancel() }
    }

    override suspend fun getChatList(): Flow<Response<ArrayList<Chat>>> = callbackFlow {
        trySend(Response.Loading())

        val listener = firebaseFirestore.collection("chats")
            .whereArrayContains("members", currentUserId)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    trySend(Response.Fail(e = Exception(error.message)))
                    close()
                }

                val chatList = arrayListOf<Chat>()

                value?.documents?.forEach { snapshot ->
                    val chat = snapshot?.toObject(Chat::class.java) as Chat
                    chatList.add(chat)
                }

                if (chatList.isNotEmpty())
                    trySend(Response.Success(data = chatList))
            }

        awaitClose {
            listener.remove()
            this.cancel()
        }
    }

    override suspend fun deleteChat(chatId: String): Flow<Response<Boolean>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateChat(chat: Chat): Flow<Response<Boolean>> = callbackFlow {
        trySend(Response.Loading())

        firebaseFirestore.collection("chats").document(chat.id!!).set(chat)
            .addOnSuccessListener {
                trySend(Response.Success(data = true))
            }.addOnFailureListener {  e ->
                trySend(Response.Fail(e = e))
            }

        awaitClose { this.cancel() }
    }

}