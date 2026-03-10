package com.example.test_sample

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TestSampleApplicationTests {

    // DBのコンテナを起動していないとフェイルするテスト。
    @Test
    fun contextLoads() {
    }

}
