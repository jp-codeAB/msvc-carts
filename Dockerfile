FROM gradle:8.10.2-jdk21 AS builder
WORKDIR /app

COPY build.gradle settings.gradle ./
COPY gradle gradle
COPY src src

RUN gradle clean build -x test --no-daemon

FROM eclipse-temurin:21-jdk
WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8086

ENV SPRING_PROFILES_ACTIVE=""
ENV CONFIG_SERVER_URI=""
ENV EUREKA_URI=""
ENV DB_HOST=""
ENV DB_PORT=""
ENV DB_NAME=""
ENV DB_USER=""
ENV DB_PASSWORD=""

ENTRYPOINT ["java", "-jar", "app.jar"]
