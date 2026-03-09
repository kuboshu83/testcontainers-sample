package com.example.test_sample.domain

interface ProductRepository {
    fun save(product: Product)
    fun deleteById(productId: String)
    fun findById(productId: String): Product?
    fun findAll(): List<Product>
}