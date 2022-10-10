package org.study.gift.api.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EntityScan(basePackages = "org.study.gift.core.entity")
public class DataSourceConfig {

    static {
        //Mysql DNS cache configuration must be invalidated due to HA.
        java.security.Security.setProperty("networkaddress.cache.ttl", "0");
        java.security.Security.setProperty("networkaddress.cache.negative.ttl", "0");
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }
}
