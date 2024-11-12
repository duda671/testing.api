package com.example.testing.api.config.database;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.cfg.Environment;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.testing.api.app*")
public class HibernateConfig {
  private final JpaProperties jpaProperties;

  public HibernateConfig(JpaProperties jpaProperties) {
    this.jpaProperties = jpaProperties;
  }

  @Bean
  JpaVendorAdapter jpaVendorAdapter() {
    return new HibernateJpaVendorAdapter();
  }

  @Bean
  @SuppressWarnings("unused")
  LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,MultiTenantConnectionProvider<String> multiTenantConnectionProviderImpl, CurrentTenantIdentifierResolver<String> currentTenantIdentifierResolverImpl) {
    Map<String, Object> jpaPropertiesMap = new HashMap<>(jpaProperties.getProperties());
    jpaPropertiesMap.put(Environment.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProviderImpl);
    jpaPropertiesMap.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, currentTenantIdentifierResolverImpl);

    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(dataSource);
    em.setPackagesToScan("com.example.testing.api*");
    em.setJpaVendorAdapter(this.jpaVendorAdapter());
    em.setJpaPropertyMap(jpaPropertiesMap);
    return em;
  }
}
