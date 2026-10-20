# ---- Stage 1 : build ----
FROM eclipse-temurin:25-jdk-alpine AS build
WORKDIR /app

# Wrapper Maven + pom.xml d'abord : le cache Docker évite de retélécharger
# les dépendances tant que le pom.xml ne change pas
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN --mount=type=cache,target=/root/.m2 ./mvnw dependency:go-offline -B

# Code source puis build du jar
COPY src ./src
RUN --mount=type=cache,target=/root/.m2 ./mvnw clean package -DskipTests -B \
    && mv target/*.jar target/app.jar

# ---- Stage 2 : runtime ----
FROM eclipse-temurin:25-jre-alpine
WORKDIR /app
RUN apk upgrade --no-cache
# Utilisateur non-root
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY --from=build --chown=spring:spring /app/target/app.jar app.jar

EXPOSE 8080

# Le JVM s'adapte à la mémoire allouée au conteneur
ENV JAVA_OPTS="-XX:MaxRAMPercentage=75"

ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar app.jar"]