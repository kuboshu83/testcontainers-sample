package com.example.test_sample.service

import com.example.test_sample.domain.User
import com.example.test_sample.domain.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val repo: UserRepository
) {
    fun saveUser(user: User) {
        repo.save(user)
    }

    fun findById(userId: String): User {
        val u = repo.findById(userId)
            ?: throw RuntimeException("ユーザーが見つかりません")
        return u
    }

    fun findAll(): List<User> {
        val users = repo.findAll()
        if (users.isEmpty()) {
            throw RuntimeException("ユーザーが見つかりません")
        }
        return users
    }

    fun deleteById(userId: String): User {
        val u = repo.findById(userId)
            ?: throw java.lang.RuntimeException("ユーザーが見つかりません")
        repo.deleteById(userId)
        return u
    }
}