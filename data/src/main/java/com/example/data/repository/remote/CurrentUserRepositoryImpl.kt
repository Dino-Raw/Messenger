package com.example.data.repository.remote

import com.example.data.storage.remote.CurrentUserStorage
import com.example.domain.model.Response
import com.example.domain.model.CurrentUser
import com.example.domain.repository.remote.CurrentUserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CurrentUserRepositoryImpl @Inject constructor(
    private val currentUserStorage: CurrentUserStorage
): CurrentUserRepository {
    override suspend fun insert(currentUser: CurrentUser): Flow<Response<Boolean>> =
        currentUserStorage.insert(currentUser = currentUser)

    override suspend fun delete(): Flow<Response<Boolean>> =
        currentUserStorage.delete()

    override suspend fun update(currentUser: CurrentUser): Flow<Response<Boolean>> =
        currentUserStorage.update(currentUser = currentUser)

    override suspend fun getUser(): Flow<Response<CurrentUser>> =
        currentUserStorage.getUser()

    override suspend fun getUserId(): Flow<Response<String>> =
        currentUserStorage.getUserId()
}