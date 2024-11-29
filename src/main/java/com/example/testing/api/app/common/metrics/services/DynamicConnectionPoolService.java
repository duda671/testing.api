package com.example.testing.api.app.common.metrics.services;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.zaxxer.hikari.HikariDataSource;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.search.Search;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DynamicConnectionPoolService {

  private final HikariDataSource dataSource;
  private final MeterRegistry meterRegistry;
  private final int maxPoolSize = 20;

  @Scheduled(fixedDelay = 30000) 
  public void adjustPoolSize() {
    double activeConnections = getMetricValue("hikaricp.connections.active");
    double maxConnections = getMetricValue("hikaricp.connections");

    if (activeConnections >= maxConnections * 0.8 && dataSource.getMaximumPoolSize() < maxPoolSize) {
      int newPoolSize = dataSource.getMaximumPoolSize() + 5;
      dataSource.setMaximumPoolSize(newPoolSize);
      System.out.println("Aumentando pool para: " + newPoolSize);
    } else if (activeConnections < maxConnections * 0.5 && dataSource.getMaximumPoolSize() > 10) {
      int newPoolSize = dataSource.getMaximumPoolSize() - 5;
      dataSource.setMaximumPoolSize(newPoolSize);
      System.out.println("Reduzindo pool para: " + newPoolSize);
    }
  }

  private double getMetricValue(String metricName) {
    return Search.in(meterRegistry).name(metricName).gauge().value();
  }
}


