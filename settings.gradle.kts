rootProject.name = "java-advanced-course"

include("byte-buddy")
include("hash-service")
include("java-lts-11-17-21")
include("instrumentation")
include("gc-test")
include("off-heap")

include("instrumentation:perf-agent")
findProject(":instrumentation:perf-agent")?.name = "perf-agent"

include("instrumentation:business-app")
findProject(":instrumentation:business-app")?.name = "business-app"

include("instrumentation:agent-loader")
findProject(":instrumentation:agent-loader")?.name = "agent-loader"

include("memory-dump")
include("jdk-instruments")
include("reentrant-locks")
include("jmh")
include("helm-kubernetes")

include(":grpc:productinfo:java:server")
include(":grpc:productinfo:java:client")

include("resilience-example-load")
include("resilience-patterns-examples")

include("actuator")
include("admin")
include("hello-service-main")
include("observability")
include("spring-boot-3-observability-main")
include("springboot3-observablity-master")
include("template")

project(":actuator").projectDir = file("actuator/actuator")
project(":admin").projectDir = file("actuator/admin")
project(":hello-service-main").projectDir = file("actuator/hello-service-main")
project(":observability").projectDir = file("actuator/observability")
project(":spring-boot-3-observability-main").projectDir = file("actuator/spring-boot-3-observability-main")
project(":template").projectDir = file("actuator/template")



project(":resilience-example-load").projectDir = file("resilience-design-patterns/resilience-example-load")
project(":resilience-patterns-examples").projectDir = file("resilience-design-patterns/resilience-patterns-examples")

//include("java-memory:java-memory")
//include("java-memory:gc_serial_parallel_cms")


include("graalvm")
include("jmeter")
//include("virtual-threads-demo")
