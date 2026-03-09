package com.example.test_sample.config

import org.apache.ibatis.session.SqlSessionFactory
import org.flywaydb.core.Flyway
import org.mybatis.spring.SqlSessionFactoryBean
import org.mybatis.spring.annotation.MapperScan
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import javax.sql.DataSource

@Configuration
class DataSourceConfig {
    @Bean("userDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.user")
    fun userDataSource(): DataSource {
        return DataSourceBuilder.create().build()
    }

    @Bean("productDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.product")
    fun productDataSource(): DataSource {
        return DataSourceBuilder.create().build()
    }
}

@Configuration
class TransactionManager {
    @Bean("userTxManager")
    fun userTxManager(
        @Qualifier("userDataSource") dataSource: DataSource
    ): DataSourceTransactionManager {
        return DataSourceTransactionManager(dataSource)
    }

    @Bean("productTxManager")
    fun productTxManager(
        @Qualifier("productDataSource") dataSource: DataSource
    ): DataSourceTransactionManager {
        return DataSourceTransactionManager(dataSource)
    }
}

@Configuration
class FlywayConfig {
    @Bean("userFlyway", initMethod = "migrate")
    fun userFlyway(
        @Qualifier("userDataSource") dataSource: DataSource
    ): Flyway {
        return Flyway.configure()
            .dataSource(dataSource)
            .locations("db/migration/user")
            .load()
    }

    @Bean("productFlyway", initMethod = "migrate")
    fun productFlyway(
        @Qualifier("productDataSource") dataSource: DataSource
    ): Flyway {
        return Flyway.configure()
            .dataSource(dataSource)
            .locations("db/migration/product")
            .load()
    }
}

@Configuration
@MapperScan(
    basePackages = ["com.example.test_sample.infrastructure.user"],
    sqlSessionFactoryRef = "userSqlSessionFactory"
)
class UserMyBatisConfig {
    @Bean("userSqlSessionFactory")
    fun userSqlSessionFactory(
        @Qualifier("userDataSource") dataSource: DataSource
    ): SqlSessionFactory {
        val factory = SqlSessionFactoryBean()
        factory.setDataSource(dataSource)
        factory.setMapperLocations(
            *PathMatchingResourcePatternResolver()
                .getResources("classpath:/mapper/user/*.xml")
        )
        return factory.`object`!!
    }
}

@Configuration
@MapperScan(
    basePackages = ["com.example.test_sample.infrastructure.product"],
    sqlSessionFactoryRef = "productSqlSessionFactory"
)
class ProductMyBatisConfig {
    @Bean("productSqlSessionFactory")
    fun productSqlSessionFactory(
        @Qualifier("productDataSource") dataSource: DataSource
    ): SqlSessionFactory {
        val factory = SqlSessionFactoryBean()
        factory.setDataSource(dataSource)
        factory.setMapperLocations(
            *PathMatchingResourcePatternResolver()
                .getResources("classpath:/mapper/user/*.xml")
        )
        return factory.`object`!!
    }
}

