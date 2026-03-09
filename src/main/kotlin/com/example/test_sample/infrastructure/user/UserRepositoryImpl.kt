package com.example.test_sample.infrastructure.user

import com.example.test_sample.domain.User
import com.example.test_sample.domain.UserRepository
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(
    private val dao: UserDao
) : UserRepository {
    override fun save(user: User) {
        val u = UserRecord(user.id, user.name, user.email)
        dao.save(u)
    }

    override fun findById(userId: String): User? {
        return dao.findById(userId)?.let { u ->
            User(u.id, u.name, u.email)
        }
    }

    override fun findAll(): List<User> {
        return dao.findAll().map { u ->
            User(u.id, u.name, u.email)
        }
    }

    override fun deleteById(userId: String) {
        dao.deleteById(userId)
    }
}

data class UserRecord(
    val id: String,
    val name: String,
    val email: String
)

@Mapper
interface UserDao {
    fun save(
        @Param("user") user: UserRecord
    )

    fun findById(
        @Param("id") userId: String
    ): UserRecord?

    fun findAll(): List<UserRecord>

    fun deleteById(
        @Param("id") userId: String
    )
}
