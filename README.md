# BlockDemo

## Build

BlockDemo consists of two projects, a Kotlin/JVM server application and a React/JS frontend application.

First, change into the `frontend/` directory and build.

```
npm install        # Install node depandancies (this should only have to be run once.)
npm run build      # Build the react project
npm run copybuild  # Copy the built React project to resources to be served by Ktor
```

Now, we can build or run the server (which will also serve the frontend.)

On Windows,
```
gradlew.bat build
```

On macOs / Linux,
```
./gradlew build
```

## Run

```
gradlew.bat
```

# Deployment

The build process creates a self-contained fat jar. We include a Dockerfile that will use that jar to create an image that can be deployed on something like Amazon ECS.