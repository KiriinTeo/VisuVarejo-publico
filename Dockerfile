# Usamos a imagem Maven oficial para compilar o projeto sem precisar
# ter Maven instalado na máquina host.
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copia o pom.xml primeiro e baixa dependências em camada separada.
# Se só o código mudar (não o pom), essa camada fica em cache — build mais rápido.
COPY pom.xml .
RUN mvn dependency:go-offline -q

COPY src ./src
RUN mvn clean package -DskipTests -q

# ── Estágio 2: runtime ────────────────────────────────────────────────────
# JRE 17 alpine é ~100MB vs ~400MB da imagem JDK completa.
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Usuário não-root por segurança (boa prática em SaaS)
RUN addgroup -S spring && adduser -S spring -G spring
USER spring

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
