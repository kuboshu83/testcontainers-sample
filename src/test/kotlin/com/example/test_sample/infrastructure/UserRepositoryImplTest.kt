package com.example.test_sample.infrastructure

import com.example.test_sample.domain.User
import com.example.test_sample.domain.UserRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlConfig
import org.springframework.transaction.annotation.Transactional
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
        @ServiceConnection
        private val postgres = PostgreSQLContainer("postgres:18.3").apply {
            withUsername("postgres")
            withPassword("postgres")
            withDatabaseName("db")
        }
    }

    @Test
    @Transactional(transactionManager = "userTxManager")
    @Sql(
        scripts = ["/sql/insert_user.sql"],
        config = SqlConfig(dataSource = "userDataSource")
    )
    fun findByIdTest() {
        val get = repo.findById("akira_id")
        val expected = User("akira_id", "akira", "akira@example.com")
        assertEquals(expected, get)
    }

    @Test
    @Transactional(transactionManager = "userTxManager")
    @Sql(
        scripts = ["/sql/insert_user.sql"],
        config = SqlConfig(dataSource = "userDataSource")
    )
    fun deleteByIdTest2() {
        repo.deleteById("akira_id")
        assertNull(repo.findById("akira_id"))
    }
}
