package com.example.test_sample.infrastructure

import com.example.test_sample.domain.User
import com.example.test_sample.domain.UserRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlConfig
import org.springframework.transaction.annotation.Transactional
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.postgresql.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName
import kotlin.test.assertEquals

@SpringBootTest
@Testcontainers
class UserRepositoryImplTest() {

    @Autowired
    lateinit var repo: UserRepository

    companion object {
        val image: DockerImageName = DockerImageName
            .parse("com.example.k83/test-sample-db:0.1.0")
            .asCompatibleSubstituteFor("postgres")

        @Container
        private val postgres = PostgreSQLContainer(image).apply {
            withUsername("postgres")
            withPassword("postgres")
            withDatabaseName("db")
        }

        @DynamicPropertySource
        @JvmStatic
        fun registerProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.user.jdbcUrl") {
                postgres.jdbcUrl.replace("/db", "/users")
            }
            registry.add("spring.datasource.user.username") {
                postgres.username
            }
            registry.add("spring.datasource.user.password") {
                postgres.password
            }
            registry.add("spring.datasource.user.driverClassName") {
                postgres.driverClassName
            }
            registry.add("spring.datasource.product.jdbcUrl") {
                postgres.jdbcUrl.replace("/db", "/product")
            }
            registry.add("spring.datasource.product.username") {
                postgres.username
            }
            registry.add("spring.datasource.product.password") {
                postgres.password
            }
            registry.add("spring.datasource.product.driverClassName") {
                postgres.driverClassName
            }
        }
    }

    @Test
    @Transactional(transactionManager = "userTxManager")
    @Sql(
        scripts = ["/sql/user/insert_user.sql"],
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
        scripts = ["/sql/user/insert_user.sql"],
        config = SqlConfig(dataSource = "userDataSource")
    )
    fun deleteByIdTest2() {
        repo.deleteById("akira_id")
        assertNull(repo.findById("akira_id"))
    }
}
