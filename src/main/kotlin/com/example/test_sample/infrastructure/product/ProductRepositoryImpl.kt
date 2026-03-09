package com.example.test_sample.infrastructure.product

import com.example.test_sample.domain.Product
import com.example.test_sample.domain.ProductRepository
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.springframework.stereotype.Repository

@Repository
class ProductRepositoryImpl(
    private val productDao: ProductDao
) : ProductRepository {
    override fun save(product: Product) {
        val record = ProductRecord(
            product.id, product.name, product.price
        )
        productDao.save(record)
    }

    override fun deleteById(productId: String) {
        productDao.deleteById(productId)
    }

    override fun findById(productId: String): Product? {
        val p = productDao.findById(productId)
        if (p == null) {
            return null
        }
        return Product(p.id, p.name, p.price)
    }

    override fun findAll(): List<Product> {
        return productDao.findAll().map { p ->
            Product(p.id, p.name, p.price)
        }
    }
}

data class ProductRecord(
    val id: String,
    val name: String,
    val price: Int,
)

@Mapper
interface ProductDao {
    fun save(
        @Param("product") product: ProductRecord
    )

    fun deleteById(
        @Param("id") id: String
    )

    fun findById(
        @Param("id") id: String
    ): ProductRecord?

    fun findAll(): List<ProductRecord>
}