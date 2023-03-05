package com.example.domain.usecase

import com.example.domain.model.Response
import com.example.domain.model.User
import com.example.domain.repository.remote.UserRepository
import kotlinx.coroutines.flow.Flow

class GetUserListByIdUseCase(private val repository: UserRepository) {
    suspend fun execute(userIdList: ArrayList<String>): Flow<Response<ArrayList<User>>> =
        repository.getUserListById(userIdList = userIdList)
}