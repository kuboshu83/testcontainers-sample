package com.example.test_sample.service

import com.example.test_sample.domain.Product
import com.example.test_sample.domain.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val repo: ProductRepository
) {
    fun saveProduct(product: Product): Product {
        repo.save(product)
        return product
    }

    fun deleteProductById(productId: String): Product {
        val product = repo.findById(productId)
            ?: throw RuntimeException("製品が見つかりません")
        repo.deleteById(productId)
        return product
    }

    fun findAllProduct(): List<Product> {
        val products = repo.findAll()
        if (products.isEmpty()) {
            throw RuntimeException("製品が見つかりません")
        }
        return products
    }

    fun findProductById(productId: String): Product {
        return repo.findById(productId)
            ?: throw RuntimeException("製品が見つかりません")
    }
}