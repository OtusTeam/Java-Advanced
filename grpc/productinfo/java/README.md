## ``ProductInfo`` Service and Client - Java Implementation

### Building and Running Service

In order to build gradle project, Go to ``Java`` project root directory location (samples) and execute
 the following shell command,
```
./gradlew :grpc:productinfo:java:server:build
```

In order to run, Go to ``Java`` project root directory location (productinfo/java/server) and execute the following
shell command,

```
java -jar build/libs/server.jar
```

### Building and Running Client

In order to build gradle project, Go to ``Java`` project root directory location (inside samples directory) and execute
 the following shell command,
```
./gradlew :grpc:productinfo:java:client:build
```

In order to run, Go to ``Java`` project root directory location (productinfo/java/client) and execute the following
shell command,

```
java -jar build/libs/client.jar
```
