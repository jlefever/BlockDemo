# BlockDemo

## Build

BlockDemo consists of two projects, a Kotlin/JVM server application and a React/JS frontend application.

First, change into the `frontend/` directory and build.

```
npm install        # Install node dependencies (this should only have to be run once.)
npm run build      # Build the react project
npm run copybuild  # Copy the built react project to resources to be served by Ktor
```

Now, we can build or run the server (which will also serve the frontend.) We include gradle wrapper in this repository. Use `gradlew.bat` on Windows or `gradlew` on Linux / macOS.

Change back into the root directory and

```
gradlew.bat build
```

Or to just run use

```
gradlew.bat
```

The resulting jar in `build/libs` contains the entire application and could be run with just `java -jar`.

# Deploy

The build process creates a self-contained fat jar. We include a Dockerfile that can use that jar to create a self-contained image that can be deployed on something like Amazon ECS.

```
docker build .
```