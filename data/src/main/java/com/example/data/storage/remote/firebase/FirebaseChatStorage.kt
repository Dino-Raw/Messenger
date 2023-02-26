package com.example.data.storage.remote.firebase

import com.example.data.storage.remote.ChatStorage
import com.example.domain.model.Chat
import com.example.domain.model.Response
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.lang.Exception
import javax.inject.Inject

class FirebaseChatStorage @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase,
): ChatStorage {
    override suspend fun getChat(chatId: String): Flow<Response<Chat>> = callbackFlow {
        trySend(Response.Loading())

        firebaseDatabase.getReference("/Chats/$chatId").get()
            .addOnSuccessListener { chatSnapshot ->
                val chat = chatSnapshot.getValue(Chat::class.java)
                if (chat != null)
                    trySend(Response.Success(data = chat))
                else
                    trySend(Response.Fail(e = Exception("No such chat")))
            }.addOnFailureListener { e ->
                trySend(Response.Fail(e = e))
            }

        awaitClose { this.cancel() }
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