FROM frolvlad/alpine-oraclejdk8:slim

VOLUME /config
VOLUME /data

WORKDIR /

EXPOSE 5000
EXPOSE 9090

COPY mock-api-*.jar app.jar
ENTRYPOINT ["java", \
            "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5000 -Djava.security.egd=file:/dev/./urandom", \
            "-jar", \
            "/app.jar", \
            "--spring.config.location=file:/config/application.yml"]