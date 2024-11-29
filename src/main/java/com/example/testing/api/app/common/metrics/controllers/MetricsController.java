package com.example.testing.api.app.common.metrics.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.search.Search;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MetricsController {

  private final MeterRegistry meterRegistry;

  @GetMapping("/metrics")
  public ResponseEntity<?> getMetrics() {
    try {
      Map<String, Object> metrics = new HashMap<>();

      metrics.put("application_ready_time_seconds", getMetricValue("application.ready.time"));
      metrics.put("application_started_time_seconds", getMetricValue("application.started.time"));
      metrics.put("disk_free_bytes", getMetricValue("disk.free"));
      metrics.put("disk_total_bytes", getMetricValue("disk.total"));
      metrics.put("hikaricp_connections", getMetricValue("hikaricp.connections"));
      metrics.put("hikaricp_connections_active", getMetricValue("hikaricp.connections.active"));
      metrics.put("system_cpu_usage", getMetricValue("system.cpu.usage"));
      metrics.put("process_uptime_seconds", getMetricValue("process.uptime"));

      return ResponseEntity.ok(metrics);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ResponseEntity.badRequest().body("Error: " + e.getMessage());
    }
  }

  private Double getMetricValue(String metricName) {
    return Search.in(meterRegistry).name(metricName).gauge().value();
  }
}
