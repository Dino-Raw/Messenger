package com.example.data.repository.remote

import com.example.data.storage.remote.UserImagesStorage
import com.example.domain.model.Response
import com.example.domain.repository.remote.UserImagesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserImagesRepositoryImpl @Inject constructor(
    private val userImagesStorage: UserImagesStorage
): UserImagesRepository {
    override suspend fun insert(imageProfilePath: String): Flow<Response<String>> =
        userImagesStorage.insert(imageProfilePath = imageProfilePath)


    override suspend fun delete(): Flow<Response<Boolean>> =
        userImagesStorage.delete()

}