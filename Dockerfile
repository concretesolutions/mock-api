FROM openjdk:8-jdk as build

ENV BUILD_PATH /building
WORKDIR $BUILD_PATH

COPY gradlew $BUILD_PATH/
COPY gradle $BUILD_PATH/gradle
# download gradle
RUN ./gradlew --continue

COPY build.gradle $BUILD_PATH

# download dependencies
RUN ./gradlew build; exit 0

COPY src $BUILD_PATH/src
COPY mocks-test $BUILD_PATH/mocks-test
COPY backup-temp $BUILD_PATH/backup-temp

RUN ./gradlew build

#############
# Final image
FROM openjdk:8-jdk
MAINTAINER "elemental-source"

VOLUME /config
VOLUME /mocks-test
VOLUME /backup-temp

WORKDIR /

EXPOSE 5000
EXPOSE 9090

ENV APP_PARAMS "--spring.config.location=file:/config/application.yml"

COPY --from=build /building/build/libs/*.jar app.jar
ENTRYPOINT ["java", \
            "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5000 -Djava.security.egd=file:/dev/./urandom", \
            "-jar", \
            "/app.jar", \
            "$APP_PARAMS"]
