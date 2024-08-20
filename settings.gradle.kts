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

include("resilience-example-load")
include("resilience-patterns-examples")

project(":resilience-example-load").projectDir = file("resilience-design-patterns/resilience-example-load")
project(":resilience-patterns-examples").projectDir = file("resilience-design-patterns/resilience-patterns-examples")