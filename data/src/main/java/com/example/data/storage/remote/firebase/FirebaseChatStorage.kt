package com.example.data.storage.remote.firebase

import com.example.data.storage.remote.ChatStorage
import com.example.domain.model.Message
import com.example.domain.model.Response
import com.google.firebase.database.*
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

        firebaseDatabase.getReference("/Messages/${message.chatId}/").push().also { dbRef ->
            dbRef.setValue(message.apply { id = dbRef.key })
                .addOnSuccessListener {
                    trySend(Response.Success(data = true))
                }.addOnFailureListener { e ->
                    trySend(Response.Fail(e = e))
                }
        }

        awaitClose { this.cancel() }
    }

    override suspend fun updateMessage(message: Message): Flow<Response<Boolean>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMessage(messageId: String): Flow<Response<Boolean>> {
        TODO("Not yet implemented")
    }

    override suspend fun chatListener(chatId: String): Flow<Response<Message>> = callbackFlow {
        trySend(Response.Loading())

        val childEventListener = firebaseDatabase.getReference("/Messages/${chatId}").addChildEventListener(object : ChildEventListener {
            // вызывается после добавления дочернего элемента
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                snapshot.getValue(Message::class.java).also {  message ->

                    if (message != null)
                        trySend(Response.Success(data = message))
                    else
                        trySend(Response.Fail(e = Exception("Message is empty")))
                }
            }

            // вызывается после изменения дочернего элемента
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            // вызывается после удаления дочернего элемента
            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            // вызывается после изменения порядка дочерних элементов
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            // вызывается при ошибке
            override fun onCancelled(error: DatabaseError) {
                trySend(Response.Fail(e = error.toException()))
            }

        })

        awaitClose {
            firebaseDatabase.reference.removeEventListener(childEventListener)
            this.cancel()
        }
    }
}