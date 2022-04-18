ARG MAVEN_VERSION=3.8.4
ARG JAVA_VERSION=11

# stage 1 and 2 are considered as different images, that's why each stage(=image) has its own cache
# stage 1 - package app
FROM maven:${MAVEN_VERSION}-jdk-${JAVA_VERSION} as builder
WORKDIR /app
COPY .mvn/ .mvn
COPY pom.xml /app
# if pom is changed - resolve dependencies and plugins
RUN mvn -B -e dependency:resolve dependency:resolve-plugins
COPY src /app/src
RUN mvn -B -e clean package
WORKDIR /app/target
# extract jar layers
RUN java -Djarmode=layertools -jar *.jar extract

# stage 2 - create real image
FROM openjdk:${JAVA_VERSION}-jre
WORKDIR /app
COPY --from=builder /app/target/dependencies/ ./
COPY --from=builder /app/target/spring-boot-loader/ ./
COPY --from=builder /app/target/snapshot-dependencies/ ./
COPY --from=builder /app/target/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]