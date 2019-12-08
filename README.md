# BlockDemo

BlockDemo is a demonstration of a blockchain. It can run as a single instance or as multiple in a peer-to-peer environment. BlockDemo consists of two projects, a Kotlin/JVM server application and a React/JS frontend application. 

## Build

Prerequisites

 - Java 8+
 - Node 20+
 - Docker 19+
 - Docker Compose 1.24+

First, change into the `frontend/` directory and build.

```
npm install        # Install node dependencies (this should only have to be run once.)
npm run build      # Build the react project
npm run copybuild  # Copy the frontend build to resources/ to be served by Ktor
```

Now, we can build or run the server. We include gradle wrapper in this repository. Use `gradlew.bat` on Windows or `gradlew` on Linux / macOS.

Change back into the root directory and

```
gradlew.bat build
```

Gradle built a jar in `build/libs` that contains the entire application and could be run with just `java -jar`.

IntelliJ can also be used to run/debug this project.

## Deploy

The build process creates a self-contained fat jar. We include a Dockerfile that can use that jar to create a self-contained image that can be deployed on something like Amazon ECS.

```
docker build -t blockdemo .
```

We can run this as a single-instance with `docker run blockdemo:latest` or as a multi-instance with `docker-compose up`.

The single instance is available at http://localhost:8080/ and the multi-instance is available at http://localhost:900X/ where X is the server #. By default, there are servers 1 through 4.