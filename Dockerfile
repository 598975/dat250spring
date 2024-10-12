# start from gradle image to build the app jar
FROM gradle:8-jdk21 AS builder

# copy necessary files and folders
COPY settings.gradle.kts gradlew build.gradle.kts ./
COPY src/ src/
COPY gradle/ gradle/

# build the project
RUN ./gradlew build

# move and rename the created jar file
RUN mv build/libs/dat250Spring-0.0.1-SNAPSHOT.jar app.jar

# image for slim project
FROM eclipse-temurin:21-alpine

# linux sh commands from lecture 14 that add a new group and user in said group to avoid running the app as root
RUN addgroup -g 1000 app
RUN adduser -G app -D -u 1000 -h /app app

# switch to the new user and application working directory
USER app
WORKDIR /app

# copy the jar file from the builder image
COPY --from=builder --chown=1000:1000 /home/gradle/app.jar . 

# command to run the app on startup of the container
CMD ["java", "-jar", "app.jar"]
