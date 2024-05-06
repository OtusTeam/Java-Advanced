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
