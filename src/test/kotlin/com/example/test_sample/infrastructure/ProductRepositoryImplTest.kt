package com.example.test_sample.infrastructure

import com.example.test_sample.domain.Product
import com.example.test_sample.infrastructure.product.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlConfig
import org.springframework.transaction.annotation.Transactional
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.postgresql.PostgreSQLContainer
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

@SpringBootTest
@Testcontainers
class ProductRepositoryImplTest() {

    @Autowired
    private lateinit var repo: ProductRepository

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