FROM openjdk:17-jdk-alpine

ENV APP_HOME=/app

RUN mkdir $APP_HOME
WORKDIR $APP_HOME

COPY target/claude-0.0.1-SNAPSHOT.jar $APP_HOME

ENV API_KEY=${API_KEY}

ENTRYPOINT ["java", "-jar", "claude-0.0.1-SNAPSHOT.jar"]