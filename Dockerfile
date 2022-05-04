FROM gradle:7.4.1-jdk-alpine as BUILD
RUN gradle --version && java -version
WORKDIR /app
COPY build.gradle settings.gradle /app/
RUN gradle clean build --no-daemon > /dev/null 2>&1 || true
COPY ./ /app/
USER root
RUN gradle clean build --no-daemon

FROM amazoncorretto:11-alpine-jdk
EXPOSE 8080
ARG JAR_FILE=/build/libs/school-registration-0.0.1-SNAPSHOT.jar
COPY --from=0 ${JAR_FILE} app.jar
CMD java \
    -Xmx3072m \
    -Xms1536m \
    -Djava.security.egd=file:/dev/./urandom \
    -jar app.jar