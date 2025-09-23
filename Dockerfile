# ================================
# Stage 1: Build com Maven + JDK 21
# ================================
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /workspace/app

# Copia apenas arquivos de config primeiro para cache das dependências
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw ./

# Baixa dependências sem compilar código ainda
RUN ./mvnw dependency:go-offline -B

# Copia código fonte
COPY src ./src

# Faz o build (sem testes, pode remover -DskipTests se quiser rodar)
RUN ./mvnw -B -DskipTests package

# ================================
# Stage 2: Runtime mais enxuto
# ================================
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copia o JAR da stage de build
COPY --from=build /workspace/app/target/*.jar app.jar

EXPOSE 8080

# Executa o Spring Boot
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
