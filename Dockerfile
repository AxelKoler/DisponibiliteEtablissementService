# ---- Stage 1 : build ----
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

# On copie d'abord le wrapper + pom.xml pour profiter du cache Docker
# (si le pom.xml ne change pas, Docker ne retéléchargera pas les dépendances)
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B

# On copie le code source et on build le jar
COPY src ./src
RUN ./mvnw clean package -DskipTests -B

# ---- Stage 2 : runtime ----
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Utilisateur non-root pour la sécurité
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
