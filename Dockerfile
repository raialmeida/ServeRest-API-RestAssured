# Uma imagem base do Maven com Java
FROM maven:3.8.7-openjdk-18-slim

# Diretório de trabalho no contêiner
WORKDIR /app

# Copie o arquivo pom.xml para o contêiner
COPY pom.xml /app/pom.xml

# Copie o código de teste para o contêiner
COPY src /app/src

RUN mkdir -p /app/target /tmp/.m2 \
    && chmod -R a+rwX /app/target /tmp/.m2
