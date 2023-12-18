package com.jparkbro.tododay.model

import kotlinx.serialization.Serializable

@Serializable
data class FirebaseDto(
    val description: String,
    val image: String,
)