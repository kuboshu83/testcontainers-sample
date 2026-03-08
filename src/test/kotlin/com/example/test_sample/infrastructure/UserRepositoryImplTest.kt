package com.example.test_sample.infrastructure

import com.example.test_sample.domain.User
import com.example.test_sample.domain.UserRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.postgresql.PostgreSQLContainer
import kotlin.test.assertEquals

@SpringBootTest
@Testcontainers
class UserRepositoryImplTest() {

    @Autowired
    lateinit var repo: UserRepository

    companion object {
        @Container
        private val postgres = PostgreSQLContainer("postgres:18.3").apply {
            withUsername("postgres")
            withPassword("postgres")
            withDatabaseName("db")
        }

        @JvmStatic
        @DynamicPropertySource
        fun registerProps(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url") { postgres.jdbcUrl }
            registry.add("spring.datasource.username") { postgres.username }
            registry.add("spring.datasource.password") { postgres.password }
        }
    }

    @Test
    fun sampleTest() {
        val user = User("akira_id", "akira", "akira@example.com")
        repo.save(user)
        val get = repo.findById("akira_id")
        val expected = User("akira_id", "akira", "akira@example.com")
        assertEquals(expected, get)
    }
}
