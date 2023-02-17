package com.example.domain.usecase

class GetRandomStringUseCase {
    fun execute(length: Int = 28): String  = (1..length)
        .map { (('A'..'Z') + ('a'..'z') + ('0'..'9')).random() }
        .joinToString("")
}