package com.example.test_sample

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.postgresql.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

@SpringBootTest
@Testcontainers
class TestSampleApplicationTests {

    companion object {
        val image: DockerImageName = DockerImageName
            .parse("com.example.k83/test-sample-db:0.1.0")
            .asCompatibleSubstituteFor("postgres")

        @Container
        val postgres = PostgreSQLContainer(image).apply {
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
    fun contextLoads() {
    }

}
