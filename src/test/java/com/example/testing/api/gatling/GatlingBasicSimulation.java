package com.example.testing.api.gatling;

import static io.gatling.javaapi.core.CoreDsl.StringBody;
import static io.gatling.javaapi.core.CoreDsl.atOnceUsers;
import static io.gatling.javaapi.core.CoreDsl.constantUsersPerSec;
import static io.gatling.javaapi.core.CoreDsl.global;
import static io.gatling.javaapi.core.CoreDsl.jsonPath;
import static io.gatling.javaapi.core.CoreDsl.rampUsers;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;
import io.gatling.javaapi.http.HttpProtocolBuilder;

public class GatlingBasicSimulation extends Simulation {
  HttpProtocolBuilder httpProtocol = http
      .baseUrl("http://localhost:8080/api")
      .acceptHeader("application/json")
      .contentTypeHeader("application/json")
      .userAgentHeader("GatlingTest/1.0");

  ScenarioBuilder getPersonScenario = scenario("Get Person by ID Scenario")
      .exec(http("Get Person by ID")
          .get("/person/1")
          .header("X-PrivateTenant", "tenantb")
          .header("Cookie", "JSESSIONID=514F426578ACED11F15622C5F0E5FED9")
          .check(status().is(200))
          .check(jsonPath("$.id").exists())
          .check(jsonPath("$.name").exists()));

  ScenarioBuilder createPersonScenario = scenario("Create Person Scenario")
      .exec(http("Create Person")
          .post("/person")
          .header("X-PrivateTenant", "tenantb")
          .header("Cookie", "JSESSIONID=514F426578ACED11F15622C5F0E5FED9")
          .body(StringBody("""
                  {
                      "name": "tenantb",
                      "age": 32,
                      "email": "3@gmail.com"
                  }
              """))
          .asJson()
          .check(status().is(200)));

  {
    setUp(

        createPersonScenario.injectOpen(
            atOnceUsers(10),
            constantUsersPerSec(10).during(15)),
        getPersonScenario.injectOpen(
            rampUsers(20).during(10)))
        .protocols(httpProtocol)
        .assertions(
            global().responseTime().max().lt(2000),
            global().successfulRequests().percent().gt(95.0));
  }
}
