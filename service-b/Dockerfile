FROM registry.access.redhat.com/ubi8/openjdk-17:1.18

WORKDIR /work/
COPY target/quarkus-app/lib/ /work/lib/
COPY target/quarkus-app/app/ /work/app/
COPY target/quarkus-app/quarkus/ /work/quarkus/
COPY target/quarkus-app/quarkus-run.jar /work/
# COPY target/quarkus-authz-*.jar /work/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/work/quarkus-run.jar"]
# ENTRYPOINT ["java", "-jar", "/work/app.jar"]