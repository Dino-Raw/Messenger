package com.example.data.repository.remote

import com.example.data.storage.remote.UserStorage
import com.example.domain.model.Response
import com.example.domain.model.CurrentUser
import com.example.domain.model.User
import com.example.domain.repository.remote.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userStorage: UserStorage
): UserRepository {
    override suspend fun getUser(userId: String): Flow<Response<User>> =
        userStorage.getUser(userId = userId)

    override suspend fun getUserList(): Flow<Response<ArrayList<User>>> =
        userStorage.getUserList()
}