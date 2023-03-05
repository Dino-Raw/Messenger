package com.example.data.storage.remote.firebase

import com.example.data.storage.remote.MessageStorage
import com.example.domain.model.Message
import com.example.domain.model.Response
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.lang.Exception
import javax.inject.Inject

class FirebaseMessageStorage @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
): MessageStorage {
    override suspend fun getMessages(chatId: String): Flow<Response<ArrayList<Message>>> = callbackFlow {
        trySend(Response.Loading())

        val listener = firebaseFirestore.collection("messages")
            .document(chatId).collection("messages")
            .orderBy("timestamp")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    trySend(Response.Fail(e = Exception(error.message)))
                    close()
                }

                val messageList = arrayListOf<Message>()

                if (value != null && !value.isEmpty)
                    value.documents.forEach {  snapshot ->
                        snapshot.toObject(Message::class.java)?.also { message ->

                            messageList.add(0, message)
                        }
                    }
                else
                    trySend(Response.Fail(e = Exception("No such messages")))

                if (messageList.isNotEmpty())
                    trySend(Response.Success(data = messageList))
            }

        awaitClose {
            listener.remove()
            this.cancel()
        }
    }

    override suspend fun insertMessage(chatId: String, message: Message): Flow<Response<Boolean>> = callbackFlow {
        trySend(Response.Loading())

        firebaseFirestore.collection("messages").document(chatId)
            .collection("messages").document().also { document ->
                document.set(
                message.also { mes ->
                    mes.id = document.id
                    mes.timestamp = Timestamp.now().seconds.toString()
                }
            ).addOnSuccessListener {
                trySend(Response.Success(data = true))
            }.addOnFailureListener { e ->
                trySend(Response.Fail(e = e))
            }
        }

        awaitClose { cancel() }
    }

    override suspend fun updateMessage(message: Message): Flow<Response<Boolean>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMessage(messageId: String): Flow<Response<Boolean>> {
        TODO("Not yet implemented")
    }
}