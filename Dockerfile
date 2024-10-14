# the base image
FROM eclipse-temurin:21
WORKDIR /app

RUN apt-get update && apt-get install -y maven

COPY . .

RUN mvn clean install

CMD ["mvn", "spring-boot:run"]