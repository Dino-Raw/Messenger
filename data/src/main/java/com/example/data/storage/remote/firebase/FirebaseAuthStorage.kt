package com.example.data.storage.remote.firebase

import com.example.data.storage.remote.AuthStorage
import com.example.domain.model.Response
import com.example.domain.model.UserAuth
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

// реализация инструмента для доступа к хранилищу
class FirebaseAuthStorage @Inject constructor(
    private val firebaseAuth: FirebaseAuth
): AuthStorage {
    override suspend fun signUp(userAuth: UserAuth): Flow<Response<Boolean>> = callbackFlow {
        trySend(Response.Loading())

        if (userAuth.email?.isNotBlank() == true && userAuth.password?.isNotBlank() == true)
            firebaseAuth.createUserWithEmailAndPassword (
                userAuth.email!!,
                userAuth.password!!,
            ).addOnSuccessListener {
                trySend(Response.Success(data = true))
            }.addOnFailureListener { e ->
                trySend(Response.Fail(e = e))
            }
        else
            trySend(Response.Fail(e = Exception("Data entry fields are empty")))

        awaitClose { this.cancel() }
    }

    override suspend fun signIn(userAuth: UserAuth): Flow<Response<Boolean>> = callbackFlow {
        trySend(Response.Loading())

        if (userAuth.email?.isNotBlank() == true && userAuth.password?.isNotBlank() == true)
            firebaseAuth.signInWithEmailAndPassword(
                userAuth.email!!,
                userAuth.password!!
            ).addOnSuccessListener {
                trySend(Response.Success(data = true))
            }.addOnFailureListener { e ->
                trySend(Response.Fail(e = e))
            }
        else
            trySend(Response.Fail(e = Exception("Data entry fields are empty")))

        awaitClose { this.cancel() }
    }

    override suspend fun signOut(): Flow<Response<Boolean>> = callbackFlow {
        trySend(Response.Loading())
        firebaseAuth.signOut()
        trySend(Response.Success(data = true))
        awaitClose { this.cancel() }
    }

    override suspend fun checkSignIn(): Flow<Response<Boolean>> = callbackFlow {
        trySend(Response.Loading())

        if (firebaseAuth.currentUser != null)
            trySend(Response.Success(data = true))
        else
            trySend(Response.Fail(Exception("")))

        awaitClose { this.cancel() }
    }

}