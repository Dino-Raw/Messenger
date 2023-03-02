package com.example.data.storage.remote.firebase

import com.example.data.storage.remote.ChatStorage
import com.example.domain.model.Chat
import com.example.domain.model.Message
import com.example.domain.model.Response
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Named

class FirebaseChatStorage @Inject constructor(
    //private val firebaseDatabase: FirebaseDatabase,
    private val firebaseFirestore: FirebaseFirestore,
    @Named("CurrentUserId") private val currentUserId: String,
): ChatStorage {
    override suspend fun getChatById(chatId: String): Flow<Response<Chat>> = callbackFlow {
        trySend(Response.Loading())
//
//        firebaseDatabase.getReference("/Chats/$chatId").get()
//            .addOnSuccessListener { chatSnapshot ->
//                val chat = chatSnapshot.getValue(Chat::class.java)
//                if (chat != null)
//                    trySend(Response.Success(data = chat))
//                else
//                    trySend(Response.Fail(e = Exception("No such chat")))
//            }.addOnFailureListener { e ->
//                trySend(Response.Fail(e = e))
//            }

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

        //val listener = firebaseFirestore.collection("chats")
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

//            .addSnapshotListener { value, error ->
//                error?.message?.also { exception ->
//                    trySend(Response.Fail(e = Exception(exception)))
//                    close()
//                }
//
//                value?.documents?.first()?.toObject(Chat::class.java)?.also { chat ->
//                    trySend(Response.Success(data = chat))
//                }
//            }

        awaitClose {
            //listener.remove()
            this.cancel()
        }
    }



    override suspend fun deleteChat(chatId: String): Flow<Response<Boolean>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateChat(chat: Chat): Flow<Response<Boolean>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertChat(chat: Chat): Flow<Response<Boolean>> {
        TODO("Not yet implemented")
    }
}