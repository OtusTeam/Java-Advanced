# Spring Boot 3 Observability Demo

This project demonstrates setting up tracing, logging, and metrics for a simple Spring Boot 3 project using
the Grafana stack.

## Getting Started

The infrastructure for Grafana is included as part of the `docker-compose.yml` file, along with the demo
service itself. There are two Dockerfiles for the service, one for a standard `.jar` and one for native.

Grafana URL: `http://localhost:3000/`

Dashboards:
* JVM Only - `https://grafana.com/grafana/dashboards/4701-jvm-micrometer/`
* JVM and Native - `https://grafana.com/grafana/dashboards/17175-spring-boot-observability/`
