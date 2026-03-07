package com.example.test_sample.controller

import com.example.test_sample.domain.User
import com.example.test_sample.service.UserService
import org.springframework.web.bind.annotation.*
import java.util.*

data class UserDTO(
    val id: String,
    val name: String,
    val email: String,
)

data class NewUserDTO(
    val name: String,
    val email: String,
)

@RestController
@RequestMapping("/api/users")
class UserController(
    private val service: UserService
) {
    @GetMapping("/{userId}")
    fun getUser(
        @PathVariable("userId") id: String
    ): UserDTO {
        val user = service.findById(id)
        return UserDTO(user.id, user.name, user.email)
    }

    @GetMapping
    fun getAllUsers(): List<UserDTO> {
        val users = service.findAll()
        return users.map { u ->
            UserDTO(u.id, u.name, u.email)
        }
    }

    @PostMapping
    fun createUser(
        @RequestBody user: NewUserDTO
    ): UserDTO {
        val id = UUID.randomUUID().toString()
        service.saveUser(
            User(id, user.name, user.email)
        )
        return UserDTO(id, user.name, user.email)
    }

    @DeleteMapping("/{userId}")
    fun deleteUserById(
        @PathVariable("userId") id: String
    ) {
        service.deleteById(id)
    }
}