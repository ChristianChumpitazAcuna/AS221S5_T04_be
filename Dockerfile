# Usar una imagen base de Java
FROM openjdk:17-jdk-alpine

# Definir las variables con ARG
ENV CLAUDE_API_KEY
ENV GEMINI_API_KEY

# Variables de entorno para la aplicación
ENV APP_HOME=/app

# Crear directorio de la aplicación
RUN mkdir $APP_HOME
WORKDIR $APP_HOME

# Copiar el archivo jar del proyecto al contenedor
COPY target/assistant-0.0.1-SNAPSHOT.jar $APP_HOME/app.jar

# Exponer el puerto de la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java","-jar","app.jar"]
