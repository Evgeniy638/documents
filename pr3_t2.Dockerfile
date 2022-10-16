FROM maven:3.8.6-ibm-semeru-11-focal
ADD . /web
WORKDIR /web

RUN apt-get update && apt-get install -y wget &&\
    wget https://www.mirea.ru/upload/medialibrary/80f/MIREA_Gerb_Colour.png -P /web/src/main/resources/static
RUN mvn clean install -DskipTests

FROM adoptopenjdk:11-jre-hotspot

LABEL student="Субботин Евгений Валерьевич ИКБО-01-19"

ENV DB_NETWORK=documents_dbpostgress
ENV DB_PORT=5433

ARG JAR_FILE=/web/target/*.jar
COPY --from=0 ${JAR_FILE} /application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]

ONBUILD RUN echo "Субботин Евгений Валерьевич"
