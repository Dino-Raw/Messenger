package com.example.data.storage.remote.firebase

import com.example.data.storage.remote.ChatStorage
import com.example.domain.model.Message
import com.example.domain.model.Response
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class FirebaseChatStorage @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase
): ChatStorage {
    override suspend fun getMessages(chatId: String): Flow<Response<ArrayList<Message>>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertMessage(message: Message): Flow<Response<Boolean>> = callbackFlow {
        trySend(Response.Loading())

        firebaseDatabase.getReference("/Messages/${message.chatId}/${message.id}").setValue(message)
            .addOnSuccessListener {
                trySend(Response.Success(data = true))
            }.addOnFailureListener { e ->
                trySend(Response.Fail(e = e))
            }

        awaitClose { this.cancel() }
    }

    override suspend fun updateMessage(message: Message): Flow<Response<Boolean>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMessage(messageId: String): Flow<Response<Boolean>> {
        TODO("Not yet implemented")
    }

    override suspend fun chatListener(): Flow<Response<Message>> {
        TODO("Not yet implemented")
    }
}