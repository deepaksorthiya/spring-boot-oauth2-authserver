# Stage 1: Build Stage
FROM bellsoft/liberica-runtime-container:jdk-21-stream-musl AS builder

WORKDIR /home/app
ADD . /home/app/spring-boot-3-oauth2-authserver
RUN cd spring-boot-3-oauth2-authserver && chmod +x mvnw && ./mvnw -Dmaven.test.skip=true clean package

# Stage 2: Layer Tool Stage
FROM bellsoft/liberica-runtime-container:jdk-21-cds-slim-musl AS optimizer

WORKDIR /home/app
COPY --from=builder /home/app/spring-boot-3-oauth2-authserver/target/*.jar spring-boot-3-oauth2-authserver.jar
RUN java -Djarmode=tools -jar spring-boot-3-oauth2-authserver.jar extract --layers --launcher

# Stage 3: Final Stage
FROM bellsoft/liberica-runtime-container:jre-21-stream-musl

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
EXPOSE 9000
COPY --from=optimizer /home/app/spring-boot-3-oauth2-authserver/dependencies/ ./
COPY --from=optimizer /home/app/spring-boot-3-oauth2-authserver/spring-boot-loader/ ./
COPY --from=optimizer /home/app/spring-boot-3-oauth2-authserver/snapshot-dependencies/ ./
COPY --from=optimizer /home/app/spring-boot-3-oauth2-authserver/application/ ./