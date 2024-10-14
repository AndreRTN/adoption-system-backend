# the base image
FROM eclipse-temurin:21

WORKDIR /app

COPY . .

RUN apt-get update && apt-get install -y dos2unix
RUN dos2unix ./mvnw

RUN ./mvnw clean install

CMD ["./mvnw", "spring-boot:run"]