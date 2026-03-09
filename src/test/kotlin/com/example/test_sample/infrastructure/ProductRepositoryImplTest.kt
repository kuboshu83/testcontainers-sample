package com.example.test_sample.infrastructure

import com.example.test_sample.domain.Product
import com.example.test_sample.infrastructure.product.ProductRepositoryImpl
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
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

@SpringBootTest
@Testcontainers
class ProductRepositoryImplTest() {

    @Autowired
    private lateinit var repo: ProductRepositoryImpl

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
    @Transactional(transactionManager = "productTxManager")
    @Sql(
        scripts = ["/sql/product/insert_product.sql"],
        config = SqlConfig(dataSource = "productDataSource")
    )
    fun findByIdTest() {
        val got = repo.findById("pro001")
        val expected = Product("pro001", "book", 200)
        assertEquals(expected, got)
    }

    @Test
    @Transactional(transactionManager = "productTxManager")
    @Sql(
        scripts = ["/sql/product/insert_product.sql"],
        config = SqlConfig(dataSource = "productDataSource")
    )
    fun deleteByIdTest() {
        // act
        repo.deleteById("pro001")
        // assert
        assertNull(repo.findById("pro001"))
    }

    @Test
    @Transactional(transactionManager = "productTxManager")
    @Sql(
        scripts = ["/sql/product/insert_product.sql"],
        config = SqlConfig(dataSource = "productDataSource")
    )
    fun findAllTest() {
        // act
        val got = repo.findAll()

        // assert
        val expected = listOf<Product>(
            Product("pro001", "book", 200),
            Product("pro002", "food", 500),
            Product("pro003", "clothes", 1000),
        )

        assertEquals(expected, got)
    }

    @Test
    @Transactional(transactionManager = "productTxManager")
    fun saveTest() {
        // act
        repo.save(Product("pro004", "game", 2000))
        // assert
        val expected = Product("pro004", "game", 2000)
        val got = repo.findById("pro004")
        assertEquals(expected, got)
    }
}