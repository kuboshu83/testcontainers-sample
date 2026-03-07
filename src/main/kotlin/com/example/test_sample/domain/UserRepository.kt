package com.example.test_sample.domain

interface UserRepository {
    fun save(user: User)
    fun deleteById(userId: String)
    fun findById(userId: String): User?
    fun findAll(): List<User>
}