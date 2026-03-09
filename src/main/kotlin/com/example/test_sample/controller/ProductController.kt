package com.example.test_sample.controller

import com.example.test_sample.domain.Product
import com.example.test_sample.service.ProductService
import org.springframework.web.bind.annotation.*
import java.util.*

data class ProductDTO(
    val id: String,
    val name: String,
    val price: Int,
)

data class NewProductDTO(
    val name: String,
    val price: Int,
)

@RestController
@RequestMapping("/api/products")
class ProductController(
    private val service: ProductService
) {
    @GetMapping
    fun findAllProduct(): List<ProductDTO> {
        return service.findAllProduct().map { p ->
            ProductDTO(p.id, p.name, p.price)
        }
    }

    @GetMapping("/{id}")
    fun findProductById(
        @PathVariable("id") id: String
    ): ProductDTO {
        val p = service.findProductById(id)
        return ProductDTO(p.id, p.name, p.price)
    }

    @PostMapping
    fun saveNewProduct(
        @RequestBody product: NewProductDTO
    ): ProductDTO {
        val id = UUID.randomUUID().toString()
        service.saveProduct(
            Product(id, product.name, product.price)
        )
        return ProductDTO(id, product.name, product.price)
    }

    @DeleteMapping("/{id}")
    fun deleteById(
        @PathVariable("id") id: String
    ): ProductDTO {
        val p = service.findProductById(id)
        service.deleteProductById(id)
        return ProductDTO(p.id, p.name, p.price)
    }
}